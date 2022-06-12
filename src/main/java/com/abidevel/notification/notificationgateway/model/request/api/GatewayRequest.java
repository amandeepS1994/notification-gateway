package com.abidevel.notification.notificationgateway.model.request.api;

import com.abidevel.notification.notificationgateway.model.enumeration.NotificationMode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GatewayRequest {
    private long customerId;
    private NotificationMode notificationMode;
    private String notificationContent;
    private String emailAddress;
    private String emailSubject;
    private String phoneNumber;
}
