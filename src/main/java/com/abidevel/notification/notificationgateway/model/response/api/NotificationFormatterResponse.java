package com.abidevel.notification.notificationgateway.model.response.api;

import lombok.Data;

@Data
public class NotificationFormatterResponse {
    private String emailSubject;
    private String emailContent;
    private String smsContent;
}
