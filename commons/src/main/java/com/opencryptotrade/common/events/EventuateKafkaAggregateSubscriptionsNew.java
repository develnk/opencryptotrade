package com.opencryptotrade.common.events;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.opencryptotrade.common.model.DebeziumModel;
import io.eventuate.SubscriberOptions;
import io.eventuate.common.id.Int128;
import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.javaclient.commonimpl.common.SerializedEvent;
import io.eventuate.local.java.common.EtopEventContext;
import io.eventuate.local.java.events.EventuateKafkaAggregateSubscriptions;
import io.eventuate.messaging.kafka.basic.consumer.EventuateKafkaConsumer;
import io.eventuate.messaging.kafka.basic.consumer.EventuateKafkaConsumerConfigurationProperties;
import io.eventuate.messaging.kafka.basic.consumer.KafkaConsumerFactory;
import io.eventuate.messaging.kafka.common.AggregateTopicMapping;
import io.eventuate.messaging.kafka.common.EventuateKafkaConfigurationProperties;
import io.eventuate.messaging.kafka.common.EventuateKafkaMultiMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
public class EventuateKafkaAggregateSubscriptionsNew extends EventuateKafkaAggregateSubscriptions {

    private final EventuateKafkaConfigurationProperties eventuateLocalAggregateStoreConfiguration;
    private final EventuateKafkaConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties;
    private final EventuateKafkaMultiMessageConverter eventuateKafkaMultiMessageConverter = new EventuateKafkaMultiMessageConverter();
    private final KafkaConsumerFactory kafkaConsumerFactory;

    public EventuateKafkaAggregateSubscriptionsNew(EventuateKafkaConfigurationProperties eventuateLocalAggregateStoreConfiguration, EventuateKafkaConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties, KafkaConsumerFactory kafkaConsumerFactory) {
        super(eventuateLocalAggregateStoreConfiguration, eventuateKafkaConsumerConfigurationProperties, kafkaConsumerFactory);
        this.eventuateKafkaConsumerConfigurationProperties = eventuateKafkaConsumerConfigurationProperties;
        this.eventuateLocalAggregateStoreConfiguration = eventuateLocalAggregateStoreConfiguration;
        this.kafkaConsumerFactory = kafkaConsumerFactory;
    }

    private final List<EventuateKafkaConsumer> consumers = new ArrayList<>();

    @PreDestroy
    public void cleanUp() {
        synchronized (consumers) {
            consumers.stream().forEach(EventuateKafkaConsumer::stop);
        }
        LOGGER.debug("Waiting for consumers to commit");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            LOGGER.error("Error waiting", e);
        }
    }

    private void addConsumer(EventuateKafkaConsumer consumer) {
        synchronized (consumers) {
            consumers.add(consumer);
        }
    }

    @Override
    public CompletableFuture<?> subscribe(String subscriberId, Map<String, Set<String>> aggregatesAndEvents,
                                          SubscriberOptions subscriberOptions,
                                          Function<SerializedEvent, CompletableFuture<?>> handler) {
        LOGGER.info("Subscribing: subscriberId = {}, aggregatesAndEvents = {}, options = {}", subscriberId, aggregatesAndEvents, subscriberOptions);
        List<String> topics = aggregatesAndEvents.keySet()
                .stream()
                .map(AggregateTopicMapping::aggregateTypeToTopic)
                .collect(toList());
        LOGGER.info("Creating consumer: subscriberId = {}, aggregatesAndEvents = {}, options = {}", subscriberId, aggregatesAndEvents, subscriberOptions);
        EventuateKafkaConsumer consumer = new EventuateKafkaConsumer(subscriberId, (record, callback) -> {
            List<SerializedEvent> serializedEvents = toSerializedEvents(record);

            for (SerializedEvent se : serializedEvents) {
                if (aggregatesAndEvents.get(se.getEntityType()).contains(se.getEventType())) {
                    handler.apply(se).whenComplete((result, t) -> {
                        callback.accept(null, t);
                    });
                } else {
                    callback.accept(null, null);
                }
            }
            return null;
        }, topics, eventuateLocalAggregateStoreConfiguration.getBootstrapServers(), eventuateKafkaConsumerConfigurationProperties, kafkaConsumerFactory);
        addConsumer(consumer);
        LOGGER.info("Starting consumer: subscriberId = {}, aggregatesAndEvents = {}, options = {}", subscriberId, aggregatesAndEvents, subscriberOptions);
        consumer.start();
        LOGGER.info("Subscribed: subscriberId = {}, aggregatesAndEvents = {}, options = {}", subscriberId, aggregatesAndEvents, subscriberOptions);
        return CompletableFuture.completedFuture(null);
    }

    private List<SerializedEvent> toSerializedEvents(ConsumerRecord<String, byte[]> record) {
        return eventuateKafkaMultiMessageConverter.convertBytesToValues(record.value())
                .stream()
                .map(value -> jsonToSerializedEvent(value, record))
                .collect(Collectors.toList());
    }

    private SerializedEvent jsonToSerializedEvent(String value, ConsumerRecord<String, byte[]> record) {
        try {
            DebeziumModel dm = JSonMapper.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).readValue(value, DebeziumModel.class);
            return new SerializedEvent(
                    Int128.fromString(dm.getPayload().getAfter().getEventId()),
                    dm.getPayload().getAfter().getEntityId(),
                    dm.getPayload().getAfter().getEntityType(),
                    dm.getPayload().getAfter().getEventData(),
                    dm.getPayload().getAfter().getEventType(),
                    record.partition(),
                    record.offset(),
                    EtopEventContext.make(dm.getPayload().getAfter().getEventId(), record.topic(), record.partition(), record.offset()),
                    dm.getPayload().getAfter().getMetadata());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
