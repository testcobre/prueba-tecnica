package com.avivas.domain.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;


import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Builder
public class NotificationEventWebHookRequest {
    @JsonProperty("event_id")
    private String eventId;
    @JsonProperty("event_type")
    private String eventType;
    @JsonProperty("content")
    private String content;
    @JsonProperty("delivery_status")
    private String deliveryStatus;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("delivery_date")
    private OffsetDateTime deliveryDate;
}
