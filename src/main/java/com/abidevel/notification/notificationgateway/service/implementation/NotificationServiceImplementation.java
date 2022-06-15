package com.abidevel.notification.notificationgateway.service.implementation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import com.abidevel.notification.notificationgateway.model.Notification;
import com.abidevel.notification.notificationgateway.model.NotificationParameter;
import com.abidevel.notification.notificationgateway.model.TemplateRequest;
import com.abidevel.notification.notificationgateway.model.enumeration.NotificationMode;
import com.abidevel.notification.notificationgateway.model.enumeration.TemplateType;
import com.abidevel.notification.notificationgateway.model.request.NotificationRequest;
import com.abidevel.notification.notificationgateway.model.request.api.CustomerPreferenceRequest;
import com.abidevel.notification.notificationgateway.model.request.api.GatewayRequest;
import com.abidevel.notification.notificationgateway.model.request.api.NotificationFormatterRequest;
import com.abidevel.notification.notificationgateway.model.response.api.CustomerPreferenceResponse;
import com.abidevel.notification.notificationgateway.model.response.api.NotificationFormatterResponse;
import com.abidevel.notification.notificationgateway.repository.NotificationRepository;
import com.abidevel.notification.notificationgateway.service.NotificationService;
import com.abidevel.notification.notificationgateway.service.api.CustomerPreferenceApiService;
import com.abidevel.notification.notificationgateway.service.api.GatewayApiService;
import com.abidevel.notification.notificationgateway.service.api.NotificationFormatterApiService;
import com.abidevel.notification.notificationgateway.utility.ObjectMapperUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

import brave.ScopedSpan;
import brave.Tracer;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImplementation implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final CustomerPreferenceApiService customerPreferenceApiService;
    private final NotificationFormatterApiService notificationFormatterApiService;
    private final GatewayApiService gatewayApiService;
    private final ObjectMapper objectMapper;
    private final Tracer tracer;
    

    public NotificationServiceImplementation (CustomerPreferenceApiService customerPreferenceApiService, NotificationRepository notificationRepository, ObjectMapper objectMapper, NotificationFormatterApiService notificationFormatterApiService, GatewayApiService gatewayApiService, Tracer tracer) {
        this.customerPreferenceApiService = customerPreferenceApiService;
        this.notificationRepository = notificationRepository;
        this.objectMapper = objectMapper;
        this.notificationFormatterApiService = notificationFormatterApiService;
        this.gatewayApiService = gatewayApiService;
        this.tracer = tracer;
    }

    @Override
    public Optional<Long> generateNotification(NotificationRequest notificationRequest) {
        if (Objects.nonNull(notificationRequest)) {
            log.info("Sending Payment.");
           Notification notification = notificationRepository.save(objectMapper.convertValue(notificationRequest, Notification.class));
           ScopedSpan newSpan = tracer.startScopedSpan("save Notifications Info");
           newSpan.tag("Save in Notification DB", "h2");
           newSpan.annotate("Client received");
           newSpan.finish();
            Optional<CustomerPreferenceResponse> response = customerPreferenceApiService.retrieveCustomerPreference(CustomerPreferenceRequest.builder().customerId(notificationRequest.getCustomerId()).build());
            if (response.isPresent()) {
                Optional<NotificationFormatterRequest> optionalNotificationFormatRequest = constructTemplateRequest(response.get(), notificationRequest);
                if (optionalNotificationFormatRequest.isPresent()) {
                    Optional<NotificationFormatterResponse> optionalNotificationResponse = notificationFormatterApiService.formatCustomerNotification(optionalNotificationFormatRequest.get());
                    if (optionalNotificationResponse.isPresent()) {
                        Optional<GatewayRequest> optionalGatewayRequest = prepareCommunicationRequest(optionalNotificationResponse.get(), response.get(), notificationRequest);
                        if (optionalGatewayRequest.isPresent()) {
                            return gatewayApiService.generateNotification(optionalGatewayRequest.get()) ? Optional.of(notification.getId()) : Optional.empty();
                        }
                    } 
                } 
            }
        }
        return Optional.empty();
    }

    private Optional<NotificationFormatterRequest> constructTemplateRequest(CustomerPreferenceResponse customerPreference, NotificationRequest notificationRequest) {
        if (verifyNotificationParameters(notificationRequest.getNotificationParameters(), notificationRequest.getTemplateType())) {
            NotificationFormatterRequest notificationFormatterRequest = new NotificationFormatterRequest();
            notificationFormatterRequest.setNotificationParameters((List<TemplateRequest>) ObjectMapperUtility.mapAllEntities(notificationRequest.getNotificationParameters(), TemplateRequest.class));
            notificationFormatterRequest.getNotificationParameters().add(
                TemplateRequest.builder()
                .notificationParameterName("name")
                .notificationParameterValue(customerPreference.getCustomerName())
                .build());
            notificationFormatterRequest.setNotificationTemplateName(notificationRequest.getTemplateType());
            notificationFormatterRequest.setNotificationMode(notificationRequest.getNotificationMode());
            return Optional.of(notificationFormatterRequest);
        }
        return Optional.empty();
    }

    private boolean verifyNotificationParameters (List<NotificationParameter> parameters, TemplateType templateType) {
        if (templateType.equals(TemplateType.PHONENUMBERCHANGED) && parameters.stream()
            .filter(parameter ->  parameter.getNotificationParameterName().equals("oldPhoneNumber") || parameter.getNotificationParameterName().equals("newPhoneNumber"))
            .collect(Collectors.toSet())
            .size() >= 2 ) {
                return true;
            }
        
        

        if (templateType.equals(TemplateType.VIEWBALANCE) && !parameters.stream()
            .filter(parameter ->  parameter.getNotificationParameterName().equals("account"))
            .collect(Collectors.toSet())
            .isEmpty()) {
                return true;
        }
        return false;
    }

    private Optional<GatewayRequest> prepareCommunicationRequest (NotificationFormatterResponse formatterResponse, CustomerPreferenceResponse customerPreferenceResponse, NotificationRequest notificationRequest) {
        if (Objects.nonNull(formatterResponse) && Objects.nonNull(notificationRequest)) {
            GatewayRequest gatewayRequest = GatewayRequest.builder()
            .customerId(notificationRequest.getCustomerId())
            .notificationMode(notificationRequest.getNotificationMode())
            .build();
            if (notificationRequest.getNotificationMode().equals(NotificationMode.EMAIL) && !formatterResponse.getEmailContent().isEmpty()) {
                gatewayRequest.setEmailAddress(customerPreferenceResponse.getEmailAddress());
                gatewayRequest.setEmailSubject(formatterResponse.getEmailSubject());
                gatewayRequest.setNotificationContent(formatterResponse.getEmailContent());
                return Optional.of(gatewayRequest);
            }
            if (notificationRequest.getNotificationMode().equals(NotificationMode.SMS) && !formatterResponse.getSmsContent().isEmpty()) {
                gatewayRequest.setNotificationContent(formatterResponse.getSmsContent());
                gatewayRequest.setPhoneNumber(customerPreferenceResponse.getPhoneNumber());
                return Optional.of(gatewayRequest);
            }
        }
        return Optional.empty();
    }

    
}
