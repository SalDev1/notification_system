package com.example.notification_delivery_sys.config;

import jakarta.validation.ValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaErrorHandlerConfig {
//    This is a combination of DefaultErrorHandler + DeadLetterPublishingRecoverer
    @Bean
    public DeadLetterPublishingRecoverer errorHandler(KafkaTemplate<?,?> kafkaTemplate) {
        // Routes failed notification records to <source-topic>.DLT preserving the source partition.
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);

        // Retry up to 3 times in each 1 second interval before invoking the recoverer.
        FixedBackOff fixedBackOff = new FixedBackOff(1000L, 3L);

        DefaultErrorHandler handler = new DefaultErrorHandler(recoverer,fixedBackOff);

        // These exception types bypass retries entirely and go straight to the DLT.
        handler.addNotRetryableExceptions(
                DeserializationException.class,
                ValidationException.class
        );
        return recoverer;
    }
}
