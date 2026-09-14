package com.example.notification_delivery_sys.controller;

import com.example.notification_delivery_sys.dto.user_preference.UserPreferenceReq;
import com.example.notification_delivery_sys.dto.user_preference.UserPreferenceRes;
import com.example.notification_delivery_sys.dto.user_preference.UserUpdatePreferenceReq;
import com.example.notification_delivery_sys.dto.user_preference.UserUpdatePreferenceRes;
import com.example.notification_delivery_sys.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class UserPreferenceController {

    @Autowired
    public UserPreferenceService userPreferenceService;

    @PostMapping("/v1/user_preference")
    public ResponseEntity<UserPreferenceRes> createUserPreference(@RequestBody UserPreferenceReq req) {
        UserPreferenceRes userPrefResponse = userPreferenceService.saveUserPreference(req);
        return ResponseEntity.ok(userPrefResponse);
    }

    @PutMapping("/v1/user_preference/update")
    public ResponseEntity<UserUpdatePreferenceRes> updateUserPreference(@RequestBody UserUpdatePreferenceReq req) {
        UserUpdatePreferenceRes userPrefResponse = userPreferenceService.updateUserPreference(req);
        return ResponseEntity.ok(userPrefResponse);
    }
}
