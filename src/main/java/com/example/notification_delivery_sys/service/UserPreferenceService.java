package com.example.notification_delivery_sys.service;

import com.example.notification_delivery_sys.dto.user_preference.UserPreferenceReq;
import com.example.notification_delivery_sys.dto.user_preference.UserPreferenceRes;
import com.example.notification_delivery_sys.dto.user_preference.UserUpdatePreferenceReq;
import com.example.notification_delivery_sys.dto.user_preference.UserUpdatePreferenceRes;
import com.example.notification_delivery_sys.entity.UserPreferenceRecord;
import com.example.notification_delivery_sys.enums.NotificationCategory;
import com.example.notification_delivery_sys.enums.NotificationChannel;

import java.util.Optional;
import java.util.UUID;

public interface UserPreferenceService {

    public UserPreferenceRes saveUserPreference(UserPreferenceReq req);

    public Optional<UserPreferenceRecord> findByUserIdByChannelByCategory(UUID userId, NotificationChannel channel, NotificationCategory category);

    public UserUpdatePreferenceRes updateUserPreference(UserUpdatePreferenceReq req);
}
