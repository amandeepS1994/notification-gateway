package com.abidevel.notification.notificationgateway.model.response.api;

import lombok.Data;

@Data
public class CustomerPreferenceResponse {
    private Long id;
    private Long customerId;
    private boolean smsPreferenceFlag;
    private boolean emailPreferenceFlag;
    private String phoneNumber;
    private String emailAddress;
    private String customerName;


}
