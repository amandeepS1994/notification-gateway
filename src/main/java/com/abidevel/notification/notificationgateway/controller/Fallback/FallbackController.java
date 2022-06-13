package com.abidevel.notification.notificationgateway.controller.Fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.abidevel.notification.notificationgateway.model.response.ApiResponse;

@RestController
public class FallbackController {
    
    public ApiResponse<Object> communicationFallback () {
        return  ApiResponse.builder().status(true).message("Service is currently unavailable, and will retry once service is available.").build();
    }
}
