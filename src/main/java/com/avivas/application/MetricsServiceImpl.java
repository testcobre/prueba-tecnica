package com.avivas.application;

import io.micrometer.core.instrument.MeterRegistry;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
class MetricsServiceImpl implements MetricsService {
    private final AtomicInteger requestCounter = new AtomicInteger(0);
    private final AtomicInteger errorCounter = new AtomicInteger(0);
    private final AtomicInteger successCounter = new AtomicInteger(0);

    public MetricsServiceImpl(MeterRegistry meterRegistry) {
        meterRegistry.gauge("send_request_by_minute", requestCounter);
        meterRegistry.gauge("send_errors_by_minute", errorCounter);
        meterRegistry.gauge("send_success_by_minute", successCounter);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            requestCounter.set(0);
            errorCounter.set(0);
            successCounter.set(0);
        }, 0, 1, TimeUnit.MINUTES);
    }

    public int incrementRequestCount() {
        return this.requestCounter.incrementAndGet();
    }

    public int incrementErrorCount() {
        return this.errorCounter.incrementAndGet();
    }

    public int incrementSuccessCount() {
        return this.successCounter.incrementAndGet();
    }
}