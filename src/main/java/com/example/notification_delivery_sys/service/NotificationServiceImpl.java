package com.example.notification_delivery_sys.service;

import com.example.notification_delivery_sys.dto.notification.NotificationReq;
import com.example.notification_delivery_sys.dto.notification.NotificationRes;
import com.example.notification_delivery_sys.entity.NotificationRecord;
import com.example.notification_delivery_sys.entity.UserPreferenceRecord;
import com.example.notification_delivery_sys.enums.NotificationStatus;
import com.example.notification_delivery_sys.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    public NotificationRepository notificationRepository;

    @Autowired
    public UserPreferenceService userPreferenceService;

    @Override
    public NotificationRes saveNotificationResponse(NotificationReq notificationRequest) {

        Optional<UserPreferenceRecord> userPreference = userPreferenceService
                .findByUserIdByChannelByCategory(
                        notificationRequest.getUserId(),
                        notificationRequest.getChannel() ,
                        notificationRequest.getCategory());

        if(userPreference.isPresent() && !userPreference.get().isEnabled()) {
            System.out.println("User not allowed to receive notification");
            return null;
        }

        NotificationRecord newNotificationRecord = new NotificationRecord()
                .builder()
                .status(NotificationStatus.PROCESSING)
                .message(notificationRequest.getMessage())
                .recipient(notificationRequest.getRecipient())
                .subject(notificationRequest.getSubject())
                .createdAt(new Date())
                .updatedAt(new Date())
                .retryCount(0)
                .build();

        notificationRepository.save(newNotificationRecord);

        NotificationRes finalHttpResponse = new NotificationRes()
                .builder()
                .notificationId(newNotificationRecord.getId())
                .status(NotificationStatus.ACCEPTED)
                .build();

        return finalHttpResponse;
    }

    @Override
    public Optional<NotificationRecord> findNotificationDetailsById (UUID notificationId) {
        return notificationRepository.findById(notificationId);
    }

    @Override
    public Optional<NotificationRecord> fetchLatestStatusByNotificationId(UUID notificationId) {
        return notificationRepository.findById(notificationId);
    }
}
