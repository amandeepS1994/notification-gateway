package com.abidevel.notification.notificationgateway.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private long notificationReferenceId;
}
