package com.opencryptotrade.cryptocurrencyservice.domain.snapshot;

import com.opencryptotrade.cryptocurrencyservice.domain.CryptoCurrency;
import com.opencryptotrade.cryptocurrencyservice.domain.command.CreateCryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.command.UpdateCryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.command.UpdateCryptoCurrencyDaemonStatusCommand;
import io.eventuate.*;
import io.eventuate.common.id.Int128;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CryptoCurrencySnapshotStrategy implements SnapshotStrategy {

    @Override
    public Class<?> getAggregateClass() {
        return CryptoCurrency.class;
    }

    @Override
    public Optional<Snapshot> possiblySnapshot(Aggregate aggregate, Optional<Int128> snapshotVersion, List<EventWithMetadata> oldEvents, List<Event> newEvents) {
        CryptoCurrency cryptoCurrency = (CryptoCurrency) aggregate;
        return Optional.of(new CryptoCurrencySnapshot(cryptoCurrency.getType(), cryptoCurrency.getSymbol(), cryptoCurrency.getSettings(), cryptoCurrency.getStatus()));
    }

    @Override
    public Aggregate recreateAggregate(Class<?> clasz, Snapshot snapshot, MissingApplyEventMethodStrategy missingApplyEventMethodStrategy) {
        CryptoCurrencySnapshot cryptoCurrencySnapshot = (CryptoCurrencySnapshot) snapshot;
        CryptoCurrency aggregate = new CryptoCurrency();
        List<Event> createEvents = aggregate.process(new CreateCryptoCurrencyCommand(
                cryptoCurrencySnapshot.getType(),
                cryptoCurrencySnapshot.getSymbol(),
                cryptoCurrencySnapshot.getSettings()
        ));
        List<Event> updateEvents = aggregate.process(new UpdateCryptoCurrencyCommand(
                cryptoCurrencySnapshot.getSymbol(),
                cryptoCurrencySnapshot.getSettings()
        ));
        List<Event> updateDaemonStatus = aggregate.process(new UpdateCryptoCurrencyDaemonStatusCommand(aggregate.getStatus()));
        List<Event> events = Stream.of(createEvents, updateEvents, updateDaemonStatus)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Aggregates.applyEventsToMutableAggregate(aggregate, events, missingApplyEventMethodStrategy);
        return aggregate;
    }
}
