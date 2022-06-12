package com.abidevel.notification.notificationgateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Data;

@Configuration
@PropertySource(value = "classpath:notification.properties")
@Data
public class WebClientConfiguration {
    private final String notificationPreferenceURL;
    private final String notificationTemplateFormatterURL;
    private final String notificationGatewayURL;


    public WebClientConfiguration(@Value("${spring.notification.preference-url}") String notificationPreferenceURL, @Value("${spring.notification.templateFormatter-url}") String notificationTemplateFormatterURL, @Value("${spring.notification.gateway-url}") String notificationGatewayURL) {
        this.notificationPreferenceURL = notificationPreferenceURL;
        this.notificationTemplateFormatterURL = notificationTemplateFormatterURL;
        this.notificationGatewayURL = notificationGatewayURL;
    }


    @Bean(name = "customerPreference")
    public WebClient retrieveCustomerPreferenceClient() {
        return WebClient.builder()
            .baseUrl(this.getNotificationPreferenceURL())
            .build();
    }

    @Bean(name = "notificationFormatter")
    public WebClient retrieveNotificationTemplateClient() {
        return WebClient.builder()
            .baseUrl(this.getNotificationTemplateFormatterURL())
            .build();
    }
    
    @Bean(name = "gateway")
    public WebClient retrieveCustomerPreferenceGatewayClient() {
        return WebClient.builder()
            .baseUrl(this.getNotificationGatewayURL())
            .build();
    }

  
}