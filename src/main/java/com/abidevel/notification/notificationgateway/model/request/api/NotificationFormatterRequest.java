package com.abidevel.notification.notificationgateway.model.request.api;

import com.abidevel.notification.notificationgateway.model.TemplateRequest;
import com.abidevel.notification.notificationgateway.model.enumeration.NotificationMode;
import com.abidevel.notification.notificationgateway.model.enumeration.TemplateType;
import lombok.Data;

import java.util.List;

@Data
public class NotificationFormatterRequest {
    private List<TemplateRequest> notificationParameters;
    private TemplateType notificationTemplateName;
    private NotificationMode notificationMode;
}
