package com.abidevel.notification.notificationgateway.controller;

import org.springframework.web.bind.annotation.RestController;

import com.abidevel.notification.notificationgateway.model.request.NotificationRequest;
import com.abidevel.notification.notificationgateway.model.response.ApiResponse;
import com.abidevel.notification.notificationgateway.model.response.NotificationResponse;
import com.abidevel.notification.notificationgateway.service.NotificationService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(path = "notifications/")
public class NotificationController {

    private final NotificationService notificationService;
    
    public NotificationController (NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(deprecated = false, method = "post")
    @PostMapping(value="")
    public ResponseEntity<ApiResponse<Object>> handleNotificationRequest(@RequestBody NotificationRequest notfiicationRequest) {
        if (Objects.nonNull(notfiicationRequest)) {
            Optional<Long> notificationId = notificationService.generateNotification(notfiicationRequest);
            return notificationId.isPresent() ?
                 new ResponseEntity<>(ApiResponse.builder()
                    .status(true)
                    .message("Notification Generated.")
                    .data(NotificationResponse.builder()
                        .notificationReferenceId(notificationId.get())
                        .build())
                    .build(), HttpStatus.ACCEPTED) :
                new ResponseEntity<>(ApiResponse.builder()
                    .status(false)
                    .message("Failed to Generate Notification")
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(ApiResponse.builder()
            .status(false)
            .message("Invalid Request")
            .build(), HttpStatus.BAD_REQUEST);
    }
    

}
