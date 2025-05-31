package com.avivas.domain.entity;

import java.time.OffsetDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "notification_event")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Builder
public class NotificationEvent {
    @Id
    @Column(name = "event_id")
    private String eventId;
    @Column(name = "event_type")
    private String eventType;
    @Column(name = "content")
    private String content;
    @Column(name = "delivery_status")
    private String deliveryStatus;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delivery_date")
    private OffsetDateTime deliveryDate;
}
