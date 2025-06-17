package com.progressoft.clustereddatawarehouse.service;

import com.progressoft.clustereddatawarehouse.dto.FxDealCreateRequestDTO;

public interface FxDealValidationService {
    void validateCurrencies(String fromCurrency, String toCurrency);
}
