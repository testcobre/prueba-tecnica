package com.avivas.infrastructure.controllers.dto;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
public class NotificationEventGet {
    private String eventId;
    private String eventType;
    private String content;
    private OffsetDateTime deliveryDate;
    private String deliveryStatus;
}
