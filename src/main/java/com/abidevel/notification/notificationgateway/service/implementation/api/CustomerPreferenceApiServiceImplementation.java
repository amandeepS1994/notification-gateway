package com.abidevel.notification.notificationgateway.service.implementation.api;

import com.abidevel.notification.notificationgateway.model.request.api.CustomerPreferenceRequest;
import com.abidevel.notification.notificationgateway.model.response.ApiResponse;
import com.abidevel.notification.notificationgateway.model.response.api.CustomerPreferenceResponse;
import com.abidevel.notification.notificationgateway.service.api.CustomerPreferenceApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class CustomerPreferenceApiServiceImplementation implements CustomerPreferenceApiService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public CustomerPreferenceApiServiceImplementation(@Qualifier(value = "customerPreference") WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }


    @Override
    public Optional<CustomerPreferenceResponse> retrieveCustomerPreference(CustomerPreferenceRequest customerPreferenceRequest) {
        
                Optional<ApiResponse> apiResponse = this.webClient.get()
                .uri("notification/{id}/preferences/", customerPreferenceRequest.getCustomerId())
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(ApiResponse.class)
                        .blockOptional();
                
                if (apiResponse.isPresent() && apiResponse.get().isStatus()) {
                        return Optional.of(objectMapper.convertValue(apiResponse.get().getData(), CustomerPreferenceResponse.class));
                }

                return Optional.empty();
    }
}
