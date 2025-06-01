package com.avivas.application;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import com.avivas.domain.entity.NotificationEvent;

public interface NotificationEventService {
    List<NotificationEvent> getNotificationEvents(String deliveryStatus, OffsetDateTime deliveryDate, Pageable pageable);

    Optional<NotificationEvent> getNotificationEventById(String notificationEventId);

    Optional<NotificationEvent> replayNotificationEvent(String notificationEventId);
}
