package com.abidevel.notification.notificationgateway.service.implementation.api;

import com.abidevel.notification.notificationgateway.model.request.api.NotificationFormatterRequest;
import com.abidevel.notification.notificationgateway.model.response.ApiResponse;
import com.abidevel.notification.notificationgateway.model.response.api.NotificationFormatterResponse;
import com.abidevel.notification.notificationgateway.service.api.NotificationFormatterApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class NotificationFormatterApiServiceImplementation implements NotificationFormatterApiService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public NotificationFormatterApiServiceImplementation(@Qualifier(value = "notificationFormatter") WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }


    @Override
    public Optional<NotificationFormatterResponse> formatCustomerNotification(NotificationFormatterRequest notificationFormatterRequest) {
        Optional<ApiResponse> apiResponse = this.webClient.post()
        .uri("/notification/template/")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(notificationFormatterRequest), NotificationFormatterRequest.class)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .blockOptional();
        
        if (apiResponse.isPresent() && apiResponse.get().isSuccessful()) {
                return Optional.of(objectMapper.convertValue(apiResponse.get().getData(), NotificationFormatterResponse.class));
        }

        return Optional.empty();
    }
}
