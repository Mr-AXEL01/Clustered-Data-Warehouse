package com.progressoft.clustereddatawarehouse.service.impl;

import com.progressoft.clustereddatawarehouse.exception.BusinessViolationException;
import com.progressoft.clustereddatawarehouse.service.CurrencyProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FxDealValidationServiceImplTest {

    @Mock
    private CurrencyProvider currencyProvider;

    @InjectMocks
    private FxDealValidationServiceImpl validationService;

    private final String validFromCurrency = "USD";
    private final String validToCurrency = "EUR";
    private final String invalidCurrency = "INVALID";

    @Test
    void givenValidDifferentCurrencies_whenValidateCurrencies_thenShouldSucceed() {
        when(currencyProvider.isValidCurrency(validFromCurrency)).thenReturn(true);
        when(currencyProvider.isValidCurrency(validToCurrency)).thenReturn(true);

        assertThatCode(() -> validationService.validateCurrencies(validFromCurrency, validToCurrency))
                .doesNotThrowAnyException();
    }

    @Test
    void givenInvalidFromCurrency_whenValidateCurrencies_thenShouldThrowBusinessViolationException() {
        when(currencyProvider.isValidCurrency(invalidCurrency)).thenReturn(false);

        assertThatThrownBy(() -> validationService.validateCurrencies(invalidCurrency, validToCurrency))
                .isInstanceOf(BusinessViolationException.class)
                .hasMessageContaining("Invalid currency code for fromCurrency")
                .hasMessageContaining(invalidCurrency);
    }

    @Test
    void givenInvalidToCurrency_whenValidateCurrencies_thenShouldThrowBusinessViolationException() {
        when(currencyProvider.isValidCurrency(validFromCurrency)).thenReturn(true);
        when(currencyProvider.isValidCurrency(invalidCurrency)).thenReturn(false);

        assertThatThrownBy(() -> validationService.validateCurrencies(validFromCurrency, invalidCurrency))
                .isInstanceOf(BusinessViolationException.class)
                .hasMessageContaining("Invalid currency code for toCurrency")
                .hasMessageContaining(invalidCurrency);
    }

    @Test
    void givenSameCurrencies_whenValidateCurrencies_thenShouldThrowBusinessViolationException() {
        when(currencyProvider.isValidCurrency(validFromCurrency)).thenReturn(true);

        assertThatThrownBy(() -> validationService.validateCurrencies(validFromCurrency, validFromCurrency))
                .isInstanceOf(BusinessViolationException.class)
                .hasMessageContaining("From currency and to currency cannot be the same");
    }
}