package com.abidevel.notification.notificationgateway.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationParameterDTO {
    private String notificationParameterName;
    private String notificationParameterValue;
}
