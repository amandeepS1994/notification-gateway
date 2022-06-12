package com.abidevel.notification.notificationgateway.service.api;

import java.util.Optional;

import com.abidevel.notification.notificationgateway.model.request.api.CustomerPreferenceRequest;
import com.abidevel.notification.notificationgateway.model.response.api.CustomerPreferenceResponse;

public interface CustomerPreferenceApiService {

   Optional<CustomerPreferenceResponse> retrieveCustomerPreference (CustomerPreferenceRequest customerPreferenceRequest);
}
