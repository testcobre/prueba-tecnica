package com.avivas.infrastructure.controllers;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avivas.application.NotificationEventService;
import com.avivas.domain.entity.NotificationEvent;
import com.avivas.infrastructure.controllers.dto.NotificationEventGet;
import com.avivas.infrastructure.controllers.mappers.NotificationEventMapper;



@RestController
public class NotificationEventController {
    private final NotificationEventService notificationEventService;

    NotificationEventController(NotificationEventService notificationEventService) {
        this.notificationEventService = notificationEventService;
    }
    
    @GetMapping("/notification-events")
    public List<NotificationEventGet> getNotificationEvents(@RequestParam(name ="delivery_status",required = false) String deliveryStatus
                                                           ,@RequestParam(name ="delivery_date",required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) OffsetDateTime  deliveryDate
                                                           ,@RequestParam(name ="page_size",required = true,defaultValue = "10") int pageSize
                                                           ,@RequestParam(name ="page_number",required = true,defaultValue = "0") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return this.notificationEventService.getNotificationEvents(deliveryStatus,  deliveryDate, pageable)
                .stream()
                .map(NotificationEventMapper::map)
                .toList();
    }
    
    @GetMapping("/notification-events/{notification_event_id}")
    public ResponseEntity<NotificationEventGet> getNotificationEventById(@PathVariable("notification_event_id") String notificationEventId) {
        Optional<NotificationEvent> optionalNotificationEvent = this.notificationEventService.getNotificationEventById(notificationEventId);
        if (optionalNotificationEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        NotificationEvent notificationEvent = optionalNotificationEvent.get();
        return ResponseEntity.ok(NotificationEventMapper.map(notificationEvent));
    }

    @PostMapping("/notification-events/{notification_event_id}/replay")
    public NotificationEventGet getNotificationEventStatus() {
        return null;
    } 
}
