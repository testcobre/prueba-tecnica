package com.avivas.application;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MetricsServiceImplTest {

    @Mock
    private MeterRegistry meterRegistry;

    @Mock
    private Gauge mockGauge;

    private MetricsServiceImpl metricsService;

    @BeforeEach
    void setUp() {
         metricsService = new MetricsServiceImpl(meterRegistry);
    }

    @Test
    void givenMeterRegistry_WhenNew_ThenCallGauge() {
        // Given/When
        // Then
        verify(meterRegistry).gauge(eq("send_request_by_minute"), any());
        verify(meterRegistry).gauge(eq("send_errors_by_minute"), any());
        verify(meterRegistry).gauge(eq("send_success_by_minute"), any());
    }

    @Test
    void givenMetricService_WhenCallIncrementRequestCountTwoTimes_ThenReturnOneAndTwo() {
        // Given/  When
        int firstIncrement = metricsService.incrementRequestCount();
        int secondIncrement = metricsService.incrementRequestCount();

        // Then
        assertEquals(1, firstIncrement);
        assertEquals(2, secondIncrement);
    }

    @Test
    void givenMetricService_WhenCallIncrementErrorCountTwoTimes_ThenReturnOneAndTwo() {
        // Give/ When
        int firstIncrement = metricsService.incrementErrorCount();
        int secondIncrement = metricsService.incrementErrorCount();

        // Then
        assertEquals(1, firstIncrement);
        assertEquals(2, secondIncrement);
    }

    @Test
    void givenMetricService_WhenCallincrementSuccessCountTwoTimes_ThenReturnOneAndTwo() {
        // Given/ When
        int firstIncrement = metricsService.incrementSuccessCount();
        int secondIncrement = metricsService.incrementSuccessCount();

        // Then
        assertEquals(1, firstIncrement);
        assertEquals(2, secondIncrement);
    }
}