package com.example.notification_delivery_sys.repository;

import com.example.notification_delivery_sys.entity.NotificationRecord;
import com.example.notification_delivery_sys.enums.NotificationCategory;
import com.example.notification_delivery_sys.enums.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationRecord, UUID> {
    Optional<NotificationRecord> findByRecipientIgnoreCaseAndChannelAndCategory(
            String recipient, NotificationChannel channel, NotificationCategory category
    );
}
