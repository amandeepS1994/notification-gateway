package com.abidevel.notification.notificationgateway.service.api;

import com.abidevel.notification.notificationgateway.model.request.api.GatewayRequest;


public interface GatewayApiService {

    boolean generateNotification(GatewayRequest gatewayRequest);

    
}
