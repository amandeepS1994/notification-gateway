package com.abidevel.notification.notificationgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class NotificationGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationGatewayApplication.class, args);
	}

}
