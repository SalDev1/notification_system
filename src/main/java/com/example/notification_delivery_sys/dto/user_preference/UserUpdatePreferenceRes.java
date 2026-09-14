package com.example.notification_delivery_sys.dto.user_preference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePreferenceRes {
    public UUID userId;
    public String responseMessage;
}
