package com.example.notification_delivery_sys.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class NotificationMetrics {

    private final Counter notification_requests;
    private final Counter notification_high_priority_requests;
    private final Counter notification_medium_priority_requests;
    private final Counter notification_low_priority_requests;
    private final Counter notification_email_channel_requests;
    private final Counter notification_inapp_channel_requests;
    private final Counter notification_sms_channel_requests;

    public NotificationMetrics(MeterRegistry registry) {
        this.notification_requests = Counter
                .builder("notification.requests")
                .description("Total number of notification requests received")
                .register(registry);

        this.notification_high_priority_requests = Counter
                .builder("notification.requests.priority.high")
                .register(registry);

        this.notification_low_priority_requests = Counter
                .builder("notification.requests.priority.low")
                .register(registry);

        this.notification_medium_priority_requests = Counter
                .builder("notification.requests.priority.medium")
                .register(registry);

        this.notification_email_channel_requests = Counter
                .builder("notification.requests.channel.email")
                .register(registry);

        this.notification_inapp_channel_requests = Counter
                .builder("notification.requests.channel.inapp")
                .register(registry);

        this.notification_sms_channel_requests = Counter
                .builder("notification.requests.channel.sms")
                .register(registry);
    }

    public void notificationRequests() {
        notification_requests.increment();
    }

    public void notificationHighPriorityRequests() {
        notification_high_priority_requests.increment();
    }

    public void notificationMediumPriorityRequests() {
        notification_medium_priority_requests.increment();
    }

    public void notificationLowPriorityRequests() {
        notification_low_priority_requests.increment();
    }

    public void notificationEmailChannelRequests() {
        notification_email_channel_requests.increment();
    }

    public void notificationInAppChannelRequests() {
        notification_inapp_channel_requests.increment();
    }

    public void notificationSmsChannelRequests() {
        notification_sms_channel_requests.increment();
    }
}
