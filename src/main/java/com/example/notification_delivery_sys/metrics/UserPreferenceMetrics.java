package com.example.notification_delivery_sys.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class UserPreferenceMetrics {

    private final Counter user_preference_added;
    private final Counter user_preference_updated;

    public UserPreferenceMetrics(MeterRegistry registry) {
        this.user_preference_added = Counter.builder("user.preference.added")
                .description("Total number of user preferences added")
                .register(registry);

        this.user_preference_updated = Counter.builder("user.preference.updated")
                .description("Total number of user preferences updated")
                .register(registry);
    }
    public void userPreferenceAdded() {
        System.out.println("User Preference Added Count ");
        user_preference_added.increment();
    }
    public void userPreferenceUpdated() {
        System.out.println("User Preference Updated Count ");
        user_preference_updated.increment();
    }
}
