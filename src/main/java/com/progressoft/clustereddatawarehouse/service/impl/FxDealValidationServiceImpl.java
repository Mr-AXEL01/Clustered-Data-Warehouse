package com.progressoft.clustereddatawarehouse.service.impl;

import com.progressoft.clustereddatawarehouse.exception.BusinessViolationException;
import com.progressoft.clustereddatawarehouse.service.CurrencyProvider;
import com.progressoft.clustereddatawarehouse.service.FxDealValidationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@RequiredArgsConstructor
public class FxDealValidationServiceImpl implements FxDealValidationService {

    private static final Logger log = LoggerFactory.getLogger(FxDealValidationServiceImpl.class);
    private static final String FROM_CURRENCY = "fromCurrency";
    private static final String TO_CURRENCY = "toCurrency";
    private final CurrencyProvider currencyProvider;

    @Override
    public void validateCurrencies(final String fromCurrency, final String toCurrency) {
        log.debug("Validating currencies: from={}, to={}", fromCurrency, toCurrency);

        validateCurrencyCode(fromCurrency, FROM_CURRENCY);
        validateCurrencyCode(toCurrency, TO_CURRENCY);
        validateCurrenciesNotEqual(fromCurrency, toCurrency);

        log.debug("Currency validation completed successfully");
    }

    private void validateCurrencyCode(final String currencyCode, final String fieldName) {
        if (!currencyProvider.isValidCurrency(currencyCode)) {
            final var errorMessage = String.format(
                    "Invalid currency code for %s: %s. Must be a valid ISO 4217 currency code",
                    fieldName, currencyCode
            );
            log.warn("Currency validation failed: {}", errorMessage);
            throw new BusinessViolationException(errorMessage);
        }
    }

    private void validateCurrenciesNotEqual(final String fromCurrency, final String toCurrency) {
        if (Objects.equals(fromCurrency, toCurrency)) {
            final var errorMessage = String.format(
                    "From currency and to currency cannot be the same: %s",
                    fromCurrency
            );
            log.warn("Currency validation failed: {}", errorMessage);
            throw new BusinessViolationException(errorMessage);
        }
    }
}
