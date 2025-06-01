package com.avivas.application;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;


@Service
public class MetricsService {
    
    private final Counter requestCounter;
    private final Counter errorCounter;
    
    public MetricsService(MeterRegistry meterRegistry) {      
        
        this.requestCounter = Counter.builder("app_requests_total")
                .description("Total number of requests")
                .tag("type", "api")
                .register(meterRegistry);

        // Counter para errores
        this.errorCounter = Counter.builder("app_errors_total")
                .description("Total number of errors")
                .register(meterRegistry);
    }
    
    public void incrementRequestCount() {
        requestCounter.increment();
    }
    
    public void incrementErrorCount() {
        errorCounter.increment();
    }
}