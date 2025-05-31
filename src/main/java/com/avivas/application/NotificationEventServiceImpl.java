package com.avivas.application;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
 
import com.avivas.domain.entity.NotificationEvent;
import com.avivas.domain.repository.NotificationEventRepository;

@Service
public class NotificationEventServiceImpl implements NotificationEventService {

    private final NotificationEventRepository notificationEventRepository;

    NotificationEventServiceImpl(final NotificationEventRepository notificationEventRepository) {
        this.notificationEventRepository = notificationEventRepository;
    }

    @Override
    public List<NotificationEvent> getNotificationEvents(String deliveryStatus, OffsetDateTime deliveryDate, Pageable pageable) {
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
    public NotificationEvent replayNotificationEvent(String notificationEventId) {
        // Implementation logic to replay a notification event by its ID
        return null; // Placeholder return statement
    }
}
