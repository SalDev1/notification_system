package com.example.notification_delivery_sys.service;

import com.example.notification_delivery_sys.dto.NotificationReq;
import com.example.notification_delivery_sys.dto.NotificationRes;
import com.example.notification_delivery_sys.entity.NotificationRecord;
import com.example.notification_delivery_sys.entity.NotificationStatus;

import java.util.Optional;
import java.util.UUID;

public interface NotificationService {
    public NotificationRes saveNotificationResponse(NotificationReq notificationRequest);

    public Optional<NotificationRecord> findNotificationDetailsById(UUID notificationId);

    public Optional<NotificationRecord>  fetchLatestStatusByNotificationId(UUID notificationId);
}
