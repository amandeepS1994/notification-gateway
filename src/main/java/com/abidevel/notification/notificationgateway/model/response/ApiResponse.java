package com.abidevel.notification.notificationgateway.model.response;

import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ApiResponse <T> {
    private String message;
    private boolean status;
    private boolean isSuccessful;
    private T data;
    private LocalDateTime localDateTime;
}
