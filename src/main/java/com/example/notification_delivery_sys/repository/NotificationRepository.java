package com.example.notification_delivery_sys.repository;

import com.example.notification_delivery_sys.entity.NotificationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationRecord, UUID> {
}
