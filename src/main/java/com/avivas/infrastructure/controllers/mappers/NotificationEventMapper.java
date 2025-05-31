package com.avivas.infrastructure.controllers.mappers;

import com.avivas.domain.entity.NotificationEvent;
import com.avivas.infrastructure.controllers.dto.NotificationEventGet;

public class NotificationEventMapper {

    private NotificationEventMapper() {
    }

    public static NotificationEventGet map(NotificationEvent notificationEvent) {
        if (notificationEvent == null) {
            return null;
        }
        
        return NotificationEventGet.builder()
                .eventId(notificationEvent.getEventId())
                .eventType(notificationEvent.getEventType())
                .content(notificationEvent.getContent())
                .deliveryDate(notificationEvent.getDeliveryDate())
                .deliveryStatus(notificationEvent.getDeliveryStatus())
                .build();
    }
}
