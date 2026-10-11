package com.example.notification_delivery_sys.repository.notification;

import com.example.notification_delivery_sys.entity.NotificationOutboxEvent;
import com.example.notification_delivery_sys.enums.NotificationOutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxNotificationRepository extends JpaRepository<NotificationOutboxEvent, UUID> {
    public List<NotificationOutboxEvent> findAllByStatus(NotificationOutboxStatus status);
}
