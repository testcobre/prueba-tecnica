package com.avivas.application;


import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.avivas.domain.dto.NotificationEventWebHookRequest;
import com.avivas.domain.entity.NotificationEvent;
import com.avivas.domain.repository.NotificationEventRepository;
import com.avivas.domain.ws.WebhookClient;


@Service
class ReplyNotificationEventServiceImpl implements ReplyNotificationEventService {
    private final NotificationEventRepository notificationEventRepository;
    private final WebhookClient webhookClient;
    private final MetricsService metricsService;

    ReplyNotificationEventServiceImpl(final NotificationEventRepository notificationEventRepository
                                 ,final WebhookClient webhookClient
                                 ,final MetricsService metricsService
                                 ) {
        this.notificationEventRepository = notificationEventRepository;
        this.webhookClient = webhookClient;
        this.metricsService = metricsService;
    }

    @Override
    public Optional<NotificationEvent> replayNotificationEvent(String notificationEventId) {
        this.metricsService.incrementRequestCount();

        Optional<NotificationEvent> optionalNotifcaionEvent = this.notificationEventRepository.findById(notificationEventId);
        if (optionalNotifcaionEvent.isPresent()) {
            NotificationEvent notificationEvent = optionalNotifcaionEvent.get();
            boolean ok = sendMessagetoWebhook(notificationEvent);
            if (ok) {
                this.metricsService.incrementSuccessCount();
                notificationEvent.setDeliveryStatus("completed");
            } else {
                this.metricsService.incrementErrorCount();
                notificationEvent.setDeliveryStatus("failed");
            }
            this.notificationEventRepository.save(notificationEvent);
            return Optional.of(notificationEvent);
        }
        return Optional.empty();
    }

    private boolean sendMessagetoWebhook(NotificationEvent notificationEvent) {
        NotificationEventWebHookRequest notificationEventWebHookRequest = NotificationEventWebHookRequest.builder()
                .eventId(notificationEvent.getEventId())
                .eventType(notificationEvent.getEventType())
                .content(notificationEvent.getContent())
                .deliveryStatus(notificationEvent.getDeliveryStatus())
                .deliveryDate(notificationEvent.getDeliveryDate())
                .build();
        try {
            ResponseEntity<Void> responseEntity = this.webhookClient.sendWebhookNotification(notificationEventWebHookRequest);
            return responseEntity.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}
