package com.opencryptotrade.cryptocurrencyservice.daemon.poller;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ThreadPoolBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class PollingRoute extends RouteBuilder {

    private static final int DEFAULT_MIN_POOL_SIZE = 10;
    private static final int DEFAULT_MAX_POOL_SIZE = 20;
    private static final int DEFAULT_MAX_QUEUE_SIZE = -1;

    private final AtomicBoolean readyToPoll = new AtomicBoolean(true);
    private final TaskProcessor taskProcessor;

    /**
     * The minimum number of threads to keep in the task executor's thread pool. The default value is {@link #DEFAULT_MIN_POOL_SIZE}.
     */
    @Setter
    protected int minPoolSize = DEFAULT_MIN_POOL_SIZE;

    /**
     * The maximum allowed size of the thread pool for the task executor that limits the number of concurrently executed org-specific threads. The
     * default value is {@link #DEFAULT_MAX_POOL_SIZE}.
     */
    @Setter
    protected int maxPoolSize = DEFAULT_MAX_POOL_SIZE;

    /**
     * The maximum number of tasks in the work queue for the task executioner; unlimited by default.
     */
    @Setter
    protected int maxQueueSize = DEFAULT_MAX_QUEUE_SIZE;

    private String mainRouteId;

    @Autowired
    private CryptoCurrencyLookup cryptoCurrencyLookup;

    @Autowired
    private TargetCryptocurrencySplitter targetCryptocurrencySplitter;

    /**
     * The apache camel route from the given URI input; by default uses Camel Timer with a random start delay between 5 an 60 seconds and fixed rate
     * set to {@code true}.
     */
    @Setter
    protected String fromEndpointUri =
            String.format("quartz://cryptoCurrency/daemon?cron=0/30+*+*+*+*+?&triggerStartDelay=%d",
                    ThreadLocalRandom.current().nextLong(5L, 30L));

    public PollingRoute(TaskProcessor taskProcessor) {
        this.taskProcessor = taskProcessor;
    }

    @SuppressWarnings("WeakerAccess")
    public boolean isReadyToProcessNext() {
        boolean wasReadyAndNowLocked = readyToPoll.compareAndSet(true, false);
        if (wasReadyAndNowLocked) {
            LOGGER.debug("{}: Starting new polling cycle; locking route to prevent multiple cycles from starting before this cycle completes...",
                    mainRouteId);
        }
        return wasReadyAndNowLocked;
    }

    @SuppressWarnings("WeakerAccess")
    public void unlockRoute() {
        readyToPoll.set(true);
        LOGGER.debug("{}: Route unlocked and available for next polling cycle task execution.", mainRouteId);
    }

    @Override
    public void configure() throws Exception {
        configureGlobalErrorHandling();
        mainRouteId = taskProcessor.getName() + "Route";
        LOGGER.info("Configuring Camel {} to poll all companies/schemas every {} milliseconds...", mainRouteId);
        ExecutorService eventMonitorThreadPool = getTaskExecutorThreadPool();

        from(fromEndpointUri)
                .filter(method(this, "isReadyToProcessNext"))
                .routeId(mainRouteId)
                .log(LoggingLevel.DEBUG, LOGGER, "Starting new Cryptocurrency polling cycle for " + mainRouteId)
                .bean(cryptoCurrencyLookup)
                .split().method(targetCryptocurrencySplitter)
                .executorService(eventMonitorThreadPool)
                .bean(taskProcessor)
                .end()
                .log(LoggingLevel.DEBUG, LOGGER, "Completed Cryptocurrency polling cycle for " + mainRouteId)
                .bean(this, "unlockRoute");
    }


    private ExecutorService getTaskExecutorThreadPool() {
        String threadPoolName = taskProcessor.getName() + "ThreadPool";
        ThreadPoolBuilder builder = new ThreadPoolBuilder(this.getContext());
        try {
            ExecutorService pollerThreadPool = builder
                    .poolSize(minPoolSize)
                    .maxPoolSize(maxPoolSize)
                    .maxQueueSize(maxQueueSize)
                    .build(threadPoolName);
            LOGGER.info("Events Monitor Thread Pool configured with minPoolSize={}, maxPoolSize={}, maxQueueSize={}",
                    minPoolSize, maxPoolSize, maxQueueSize);
            return pollerThreadPool;
        } catch (Exception e) {
            throw new PollerException(
                    "Failed to build and initialize task executor " + threadPoolName + " for cryptocurrency polling process!", e);
        }
    }

    private void configureGlobalErrorHandling() {
        errorHandler(defaultErrorHandler().logExhaustedMessageHistory(false));

        // handle handle any unhandled system/programming/etc exceptions, log error, but do not kill router
        onException(Exception.class)
                .handled(true)
                .log(LoggingLevel.ERROR, LOGGER, "Current polling cycle aborted for " + mainRouteId + " due to critical error. ")
                .bean(this, "unlockRoute");

    }

}
