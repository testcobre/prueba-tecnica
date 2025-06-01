package com.avivas.application;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.avivas.domain.dto.NotificationEventWebHookRequest;
import com.avivas.domain.entity.NotificationEvent;
import com.avivas.domain.repository.NotificationEventRepository;
import com.avivas.domain.ws.WebhookClient;


@Service
class NotificationEventServiceImpl implements NotificationEventService {
    private final NotificationEventRepository notificationEventRepository;
    private final WebhookClient webhookClient;
    private final MetricsService metricsService;

    NotificationEventServiceImpl(final NotificationEventRepository notificationEventRepository
                                 ,final WebhookClient webhookClient
                                 ,final MetricsService metricsService
                                 ) {
        this.notificationEventRepository = notificationEventRepository;
        this.webhookClient = webhookClient;
        this.metricsService = metricsService;
    }

    @Override
    public List<NotificationEvent> getNotificationEvents(String deliveryStatus
                                                        ,OffsetDateTime deliveryDate
                                                        ,Pageable pageable) {
        if (deliveryDate == null) {
            return this.notificationEventRepository.getNotificationEvents(deliveryStatus, pageable);
        }
        return this.notificationEventRepository.getNotificationEvents(deliveryStatus, deliveryDate, pageable);
    }

    @Override
    public Optional<NotificationEvent> getNotificationEventById(String notificationEventId) {
        return this.notificationEventRepository.findById(notificationEventId);
    }

    @Override
    public Optional<NotificationEvent> replayNotificationEvent(String notificationEventId) {
        this.metricsService.incrementRequestCount();

        Optional<NotificationEvent> optionalNotifcaionEvent = this.notificationEventRepository.findById(notificationEventId);
        if (optionalNotifcaionEvent.isPresent()) {
            NotificationEvent notificationEvent = optionalNotifcaionEvent.get();
            boolean ok = sendMessagetoWebhook(notificationEvent);
            if (ok) {
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

    public boolean sendMessagetoWebhook(NotificationEvent notificationEvent) {
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
