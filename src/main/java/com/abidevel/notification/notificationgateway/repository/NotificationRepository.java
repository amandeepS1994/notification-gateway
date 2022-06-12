package com.abidevel.notification.notificationgateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abidevel.notification.notificationgateway.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
    
}
