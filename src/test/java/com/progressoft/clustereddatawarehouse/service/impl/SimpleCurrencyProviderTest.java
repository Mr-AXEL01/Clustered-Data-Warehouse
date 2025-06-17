package com.progressoft.clustereddatawarehouse.service.impl;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimpleCurrencyProviderTest {

    private final SimpleCurrencyProvider provider = new SimpleCurrencyProvider();

    @Test
    void whenGetAvailableCurrencies_thenReturnNonEmptySet() {
        Set<String> currencies = provider.getAvailableCurrencies();

        assertThat(currencies)
                .isNotNull()
                .isNotEmpty()
                .contains("USD", "MAD");
    }

    @Test
    void givenValidCurrencyCode_whenIsValidCurrency_thenReturnTrue() {
        assertThat(provider.isValidCurrency("USD")).isTrue();
        assertThat(provider.isValidCurrency("EUR")).isTrue();
    }

    @Test
    void givenInvalidCurrencyCode_whenIsValidCurrency_thenThrowIllegalArgumentException() {
        assertThatThrownBy(() -> provider.isValidCurrency("INVALID_CODE"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenNullCurrencyCode_whenIsValidCurrency_thenThrowNullPointerException() {
        assertThatThrownBy(() -> provider.isValidCurrency(null))
                .isInstanceOf(NullPointerException.class);
    }
}