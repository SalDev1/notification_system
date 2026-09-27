package com.example.notification_delivery_sys.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public KafkaAdmin.NewTopics appTopics() {

        NewTopic topic1 = TopicBuilder.name("notification.priority.high")
                .partitions(1).replicas(1).build();
        NewTopic topic2 = TopicBuilder.name("notification.priority.medium")
                .partitions(1).replicas(1).build();
        NewTopic topic3 = TopicBuilder.name("notification.priority.low")
                .partitions(1).replicas(1).build();

        return new KafkaAdmin.NewTopics(topic1, topic2 , topic3);
    }
}
