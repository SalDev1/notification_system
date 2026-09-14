package com.example.notification_delivery_sys.service;

import com.example.notification_delivery_sys.dto.user_preference.UserPreferenceReq;
import com.example.notification_delivery_sys.dto.user_preference.UserPreferenceRes;
import com.example.notification_delivery_sys.dto.user_preference.UserUpdatePreferenceReq;
import com.example.notification_delivery_sys.dto.user_preference.UserUpdatePreferenceRes;
import com.example.notification_delivery_sys.entity.UserPreferenceRecord;
import com.example.notification_delivery_sys.enums.NotificationCategory;
import com.example.notification_delivery_sys.enums.NotificationChannel;
import com.example.notification_delivery_sys.exception.NotificationNotFoundException;
import com.example.notification_delivery_sys.exception.UserPreferenceNotFoundException;
import com.example.notification_delivery_sys.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

    @Autowired
    public UserPreferenceRepository userPreferenceRepository;

    @Override
    public UserPreferenceRes saveUserPreference(UserPreferenceReq userReq) {

        UserPreferenceRecord newUserPreferenceRecord = new UserPreferenceRecord()
                .builder()
                .category(userReq.getCategory())
                .channel(userReq.getChannel())
                .quiteHoursEnd(new Date())
                .quiteHoursStart(new Date())
                .enabled(userReq.isEnabled())
                .timezone(new Date().toString())
                .build();

        userPreferenceRepository.save(newUserPreferenceRecord);

        UserPreferenceRes finalResponse = new UserPreferenceRes().builder()
                .userId(newUserPreferenceRecord.getUserId())
                .responseMessage("User Preference is Saved Sucessfully")
                .build();

        return finalResponse;
    }

    @Override
    public Optional<UserPreferenceRecord> findByUserIdByChannelByCategory(UUID userId, NotificationChannel channel, NotificationCategory category) {
        return userPreferenceRepository.findByUserIdAndChannelAndCategory(userId,channel,category);
    }

    @Override
    public UserUpdatePreferenceRes updateUserPreference(UserUpdatePreferenceReq req) {
        UserPreferenceRecord foundRecord = userPreferenceRepository
                .findById(req.getUserId())
                .orElseThrow(() -> new UserPreferenceNotFoundException("User Preference Not Found with id" + req.getUserId()));

        // Updating three parameters of the User Preference
        foundRecord.channel = req.getChannel();
        foundRecord.category = req.getCategory();
        foundRecord.enabled = req.isEnabled();

        userPreferenceRepository.save(foundRecord);

        UserUpdatePreferenceRes finalResponse = new UserUpdatePreferenceRes()
                .builder()
                .userId(req.getUserId())
                .responseMessage("User Preference is updated successfully")
                .build();

        return finalResponse;
    }
}
