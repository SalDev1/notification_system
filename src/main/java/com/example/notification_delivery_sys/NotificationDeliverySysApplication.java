package com.example.notification_delivery_sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotificationDeliverySysApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationDeliverySysApplication.class, args);
	}

}
