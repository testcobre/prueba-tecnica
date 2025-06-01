package com.avivas.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.avivas.domain.dto.NotificationEventWebHookRequest;
import com.avivas.domain.entity.NotificationEvent;
import com.avivas.domain.repository.NotificationEventRepository;
import com.avivas.domain.ws.WebhookClient;

@ExtendWith(MockitoExtension.class)
class ReplyNotificationEventServiceImplTest {

    @Mock
    private NotificationEventRepository notificationEventRepository;
    @Mock
    private WebhookClient webhookClient;
    @Mock
    private MetricsService metricsService;
    @InjectMocks
    private ReplyNotificationEventServiceImpl replyNotificationEventService;

    @BeforeEach
    void setUp() {
        replyNotificationEventService = new ReplyNotificationEventServiceImpl(
                notificationEventRepository,
                webhookClient,
                metricsService);
    }

    @Test
    void givenValidData_WhenReplayNotification_ThenReturnCompleted() {
        // Given
        String notificationEventId = "test-id-123";
        NotificationEvent notificationEvent = new NotificationEvent();
        notificationEvent.setEventId("event-" + notificationEventId);

        when(notificationEventRepository.findById(notificationEventId))
                .thenReturn(Optional.of(notificationEvent));
        when(webhookClient.sendWebhookNotification(any(NotificationEventWebHookRequest.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(notificationEventRepository.save(any(NotificationEvent.class)))
                .thenReturn(notificationEvent);

        // When
        Optional<NotificationEvent> result = replyNotificationEventService.replayNotificationEvent(notificationEventId);

        // Then
        assertTrue(result.isPresent());
        assertEquals("completed", result.get().getDeliveryStatus());

        verify(metricsService).incrementRequestCount();
        verify(metricsService).incrementSuccessCount();
        verify(metricsService, never()).incrementErrorCount();
        verify(notificationEventRepository).findById(notificationEventId);
        verify(notificationEventRepository).save(notificationEvent);
        verify(webhookClient).sendWebhookNotification(any(NotificationEventWebHookRequest.class));
    }

    @Test
    void givenSendFail_WhenReplayNotification_ThenReturnFailed() {
        // Given
        String notificationEventId = "test-id-456";
        NotificationEvent notificationEvent = new NotificationEvent();
        notificationEvent.setEventId("event-" + notificationEventId);

        when(notificationEventRepository.findById(notificationEventId))
                .thenReturn(Optional.of(notificationEvent));
        when(webhookClient.sendWebhookNotification(any(NotificationEventWebHookRequest.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        when(notificationEventRepository.save(any(NotificationEvent.class)))
                .thenReturn(notificationEvent);

        // When
        Optional<NotificationEvent> result = replyNotificationEventService.replayNotificationEvent(notificationEventId);

        // Then
        assertTrue(result.isPresent());
        assertEquals("failed", result.get().getDeliveryStatus());

        verify(metricsService).incrementRequestCount();
        verify(metricsService).incrementErrorCount();
        verify(metricsService, never()).incrementSuccessCount();
        verify(notificationEventRepository).findById(notificationEventId);
        verify(notificationEventRepository).save(notificationEvent);
        verify(webhookClient).sendWebhookNotification(any(NotificationEventWebHookRequest.class));
    }

}