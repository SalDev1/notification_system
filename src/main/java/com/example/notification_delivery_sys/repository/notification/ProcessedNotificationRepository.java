package com.example.notification_delivery_sys.repository.notification;

import com.example.notification_delivery_sys.entity.ProcessedNotificationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProcessedNotificationRepository extends JpaRepository<ProcessedNotificationRecord, String> {
}
