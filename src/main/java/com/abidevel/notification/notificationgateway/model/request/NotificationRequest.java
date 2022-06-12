package com.abidevel.notification.notificationgateway.model.request;

import java.util.List;

import com.abidevel.notification.notificationgateway.model.NotificationParameter;
import com.abidevel.notification.notificationgateway.model.enumeration.NotificationMode;
import com.abidevel.notification.notificationgateway.model.enumeration.TemplateType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationRequest {
    private long customerId;
    private NotificationMode notificationMode;
    private List<NotificationParameter> notificationParameters;
    private TemplateType templateType;
}

  