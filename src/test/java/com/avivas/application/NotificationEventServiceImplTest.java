package com.avivas.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.avivas.domain.entity.NotificationEvent;
import com.avivas.domain.repository.NotificationEventRepository;
import com.avivas.domain.ws.WebhookClient;

@ExtendWith(MockitoExtension.class)
class NotificationEventServiceImplTest {

    @Mock
    private NotificationEventRepository notificationEventRepository;
    
    @Mock
    private WebhookClient webhookClient;
    
    @Mock
    private MetricsService metricsService;

    private NotificationEventServiceImpl notificationEventService;

    @BeforeEach
    void setUp() {
        notificationEventService = new NotificationEventServiceImpl(
            notificationEventRepository,
            webhookClient,
            metricsService
        );
    }

    @Test
    void givenDeliveryDateNull_WhenGetNotificationEvents_ThenReturnExpectedValues() {
        // Given
        String deliveryStatus = "DELIVERED";
        Pageable pageable = PageRequest.of(0, 10);
        List<NotificationEvent> expectedEvents = Arrays.asList(
            new NotificationEvent(),
            new NotificationEvent()
        );

        when(notificationEventRepository.getNotificationEvents(deliveryStatus, pageable))
            .thenReturn(expectedEvents);

        // When
        List<NotificationEvent> result = notificationEventService.getNotificationEvents(
            deliveryStatus, null, pageable
        );

        // Then
        assertEquals(expectedEvents, result);
        verify(notificationEventRepository).getNotificationEvents(deliveryStatus, pageable);
    }

    @Test
    void givenValidData_WhenGetNotificationEvents_ThenReturnExpectedValues() {
        // Given
        String deliveryStatus = "PENDING";
        OffsetDateTime deliveryDate = OffsetDateTime.now();
        Pageable pageable = PageRequest.of(0, 5);
        List<NotificationEvent> expectedEvents = Arrays.asList(
            new NotificationEvent(),
            new NotificationEvent()
        );

        when(notificationEventRepository.getNotificationEvents(deliveryStatus, deliveryDate, pageable))
            .thenReturn(expectedEvents);

        // When
        List<NotificationEvent> result = notificationEventService.getNotificationEvents(
            deliveryStatus, deliveryDate, pageable
        );

        // Then
        assertEquals(expectedEvents, result);
        verify(notificationEventRepository).getNotificationEvents(deliveryStatus, deliveryDate, pageable);
        verify(notificationEventRepository, never()).getNotificationEvents(
            any(String.class), any(Pageable.class)
        );
    }

    @Test
    void givenValidData_WhenGetNotificationEventById_ThenReturnExpectedValues() {
        // Given
        String notificationEventId = "test-id-123";
        NotificationEvent expectedEvent =  new NotificationEvent();
        Optional<NotificationEvent> expectedOptional = Optional.of(expectedEvent);

        when(notificationEventRepository.findById(notificationEventId))
            .thenReturn(expectedOptional);

        // When
        Optional<NotificationEvent> result = notificationEventService.getNotificationEventById(notificationEventId);

        // Then
        assertTrue(result.isPresent());
        assertSame(expectedEvent, result.get());
        verify(notificationEventRepository).findById(notificationEventId);
    }

    @Test
    void givenNoData_WhenGetNotificationEventById_ThenReturnExpectedValues() {
        // Given
        String notificationEventId = "non-existent-id";
        Optional<NotificationEvent> emptyOptional = Optional.empty();

        when(notificationEventRepository.findById(notificationEventId))
            .thenReturn(emptyOptional);

        // When
        Optional<NotificationEvent> result = notificationEventService.getNotificationEventById(notificationEventId);

        // Then
        assertTrue(result.isEmpty());
        verify(notificationEventRepository).findById(notificationEventId);
    }
}