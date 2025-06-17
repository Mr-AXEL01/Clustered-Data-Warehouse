package com.progressoft.clustereddatawarehouse.config;

import com.progressoft.clustereddatawarehouse.mapper.FxDealMapper;
import com.progressoft.clustereddatawarehouse.mapper.FxDealMapperImpl;
import com.progressoft.clustereddatawarehouse.repository.FxDealRepository;
import com.progressoft.clustereddatawarehouse.service.CurrencyProvider;
import com.progressoft.clustereddatawarehouse.service.FxDealService;
import com.progressoft.clustereddatawarehouse.service.FxDealValidationService;
import com.progressoft.clustereddatawarehouse.service.impl.SimpleCurrencyProvider;
import com.progressoft.clustereddatawarehouse.service.impl.FxDealServiceImpl;
import com.progressoft.clustereddatawarehouse.service.impl.FxDealValidationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FxDealConfig {

    @Bean
    FxDealMapper fxDealMapper() {
        return new FxDealMapperImpl();
    }

    @Bean
    FxDealValidationService fxDealValidationService(final CurrencyProvider currencyProvider) {
        return new FxDealValidationServiceImpl(currencyProvider);
    }

    @Bean
    FxDealService fxDealService(
            final FxDealRepository repository,
            final FxDealValidationService validator,
            final FxDealMapper mapper
    ) {
        return new FxDealServiceImpl(repository, validator, mapper);
    }

    @Bean
    CurrencyProvider currencyProvider() {
        return new SimpleCurrencyProvider();
    }
}
