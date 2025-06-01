package com.avivas.application;

import java.util.Optional;
import com.avivas.domain.entity.NotificationEvent;

public interface ReplyNotificationEventService {
    Optional<NotificationEvent> replayNotificationEvent(String notificationEventId);
}
