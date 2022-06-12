package com.abidevel.notification.notificationgateway.service.api;

import com.abidevel.notification.notificationgateway.model.request.api.NotificationFormatterRequest;
import com.abidevel.notification.notificationgateway.model.response.api.NotificationFormatterResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

public interface NotificationFormatterApiService {
    Optional<NotificationFormatterResponse> formatCustomerNotification(NotificationFormatterRequest notificationFormatterRequest);
}
