package com.progressoft.clustereddatawarehouse.service;

import java.util.Set;

public interface CurrencyProvider {

    Set<String> getAvailableCurrencies();

    Boolean isValidCurrency(final String currency);
}
