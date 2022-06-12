package com.abidevel.notification.notificationgateway.service;

import java.util.Optional;

import com.abidevel.notification.notificationgateway.model.request.NotificationRequest;

public interface NotificationService {
    Optional<Long> generateNotification(NotificationRequest notificationRequest);
}
