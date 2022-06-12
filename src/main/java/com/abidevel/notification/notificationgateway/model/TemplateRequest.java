package com.abidevel.notification.notificationgateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateRequest {
    private String notificationParameterName;
    private String notificationParameterValue;

}
