package com.example.notification_delivery_sys.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic systemTopic() {
        return TopicBuilder.name("notification_created")
                .partitions(3)
                .replicas(2)
                .configs(Map.of(
                        TopicConfig.RETENTION_MS_CONFIG, "3600000"
                ))
                .build();
    }
}
