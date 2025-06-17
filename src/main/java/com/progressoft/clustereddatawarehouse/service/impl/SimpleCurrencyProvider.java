package com.progressoft.clustereddatawarehouse.service.impl;

import com.progressoft.clustereddatawarehouse.service.CurrencyProvider;

import java.util.Currency;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleCurrencyProvider implements CurrencyProvider {

    @Override
    public Set<String> getAvailableCurrencies() {
        return Currency.getAvailableCurrencies()
                .stream()
                .map(Currency::getCurrencyCode)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Boolean isValidCurrency(final String currencyCode) {
        Currency.getInstance(currencyCode);
        return getAvailableCurrencies().contains(currencyCode);
    }
}
