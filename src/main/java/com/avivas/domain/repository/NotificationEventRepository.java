package com.avivas.domain.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avivas.domain.entity.NotificationEvent;

public interface NotificationEventRepository extends JpaRepository<NotificationEvent, String> {

    @Query("SELECT ne FROM NotificationEvent ne WHERE :deliveryStatus IS NULL or ne.deliveryStatus = :deliveryStatus")
    List<NotificationEvent> getNotificationEvents(@Param("deliveryStatus") String deliveryStatus,Pageable pageable);
    @Query("SELECT ne FROM NotificationEvent ne WHERE (:deliveryStatus IS NULL or ne.deliveryStatus = :deliveryStatus) AND ne.deliveryDate = :deliveryDate")
    List<NotificationEvent> getNotificationEvents(@Param("deliveryStatus") String deliveryStatus,@Param("deliveryDate") OffsetDateTime deliveryDate, Pageable pageable);

}
