package com.abidevel.notification.notificationgateway.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.abidevel.notification.notificationgateway.model.enumeration.NotificationMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long customerId;
    @Enumerated(EnumType.STRING)
    private NotificationMode notificationMode;

    @OneToMany(mappedBy = "notification", fetch = FetchType.LAZY, targetEntity = NotificationParameter.class, cascade = {CascadeType.ALL})
    private List<NotificationParameter> notificationParameter;
    private String notificationTemplateId;
}