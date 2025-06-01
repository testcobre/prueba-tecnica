package com.avivas.application;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.avivas.domain.entity.NotificationEvent;
import com.avivas.domain.repository.NotificationEventRepository;
import com.avivas.domain.ws.WebhookClient;


@Service
class NotificationEventServiceImpl implements NotificationEventService {
    private final NotificationEventRepository notificationEventRepository;
 
    NotificationEventServiceImpl(final NotificationEventRepository notificationEventRepository
                                 ,final WebhookClient webhookClient
                                 ,final MetricsService metricsService
                                 ) {
        this.notificationEventRepository = notificationEventRepository;
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
}
