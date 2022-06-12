package com.abidevel.notification.notificationgateway.model.request.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerPreferenceRequest {
    private long customerId;
}
