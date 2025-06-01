package com.avivas.application;

public interface MetricsService {
    int incrementRequestCount();

    int incrementErrorCount();

    int incrementSuccessCount();
}
