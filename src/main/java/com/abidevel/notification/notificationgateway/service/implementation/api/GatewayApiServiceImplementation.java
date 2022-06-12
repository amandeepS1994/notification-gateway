package com.abidevel.notification.notificationgateway.service.implementation.api;

import com.abidevel.notification.notificationgateway.model.request.api.GatewayRequest;
import com.abidevel.notification.notificationgateway.model.response.ApiResponse;
import com.abidevel.notification.notificationgateway.service.api.GatewayApiService;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class GatewayApiServiceImplementation implements GatewayApiService {

    private final WebClient webClient;

    public GatewayApiServiceImplementation(@Qualifier(value = "gateway") WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public boolean generateNotification(GatewayRequest gatewayRequest) {     
        Optional<ApiResponse> apiResponse = this.webClient.post()
        .uri("/notification/")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(gatewayRequest), GatewayRequest.class)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .blockOptional();
         return (apiResponse.isPresent() && apiResponse.get().isSuccessful());
     
    }
}
