package com.abidevel.notification.notificationgateway.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class NotificationParameter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long  id;
    private String notificationParameterName;
    private String notificationParameterValue;
    @ManyToOne(targetEntity = Notification.class, fetch = FetchType.EAGER)
    private Notification notification;
}
