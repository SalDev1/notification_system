package com.example.notification_delivery_sys.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {
    private final ObjectMapper objectMapper;

    public JsonUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch(JsonProcessingException e) {
            throw new RuntimeException("Failed to serailize object to JSON");
        }
    }

    public <T> T deserialize(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json,clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize object to JSON");
        }
    }
}
