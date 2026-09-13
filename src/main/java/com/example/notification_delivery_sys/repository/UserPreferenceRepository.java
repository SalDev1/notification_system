package com.example.notification_delivery_sys.repository;

import com.example.notification_delivery_sys.entity.UserPreferenceRecord;
import com.example.notification_delivery_sys.enums.NotificationCategory;
import com.example.notification_delivery_sys.enums.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreferenceRecord, UUID> {
    Optional<UserPreferenceRecord> findByUserIdAndChannelAndCategory(UUID userId, NotificationChannel channel, NotificationCategory category);
}
