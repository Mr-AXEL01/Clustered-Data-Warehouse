package com.progressoft.clustereddatawarehouse.service.impl;

import com.progressoft.clustereddatawarehouse.domain.FxDeal;
import com.progressoft.clustereddatawarehouse.dto.FxDealCreateRequestDTO;
import com.progressoft.clustereddatawarehouse.dto.FxDealResponseDTO;
import com.progressoft.clustereddatawarehouse.exception.BusinessViolationException;
import com.progressoft.clustereddatawarehouse.mapper.FxDealMapper;
import com.progressoft.clustereddatawarehouse.repository.FxDealRepository;
import com.progressoft.clustereddatawarehouse.service.FxDealService;
import com.progressoft.clustereddatawarehouse.service.FxDealValidationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class FxDealServiceImpl implements FxDealService {

    private static final Logger log = LoggerFactory.getLogger(FxDealServiceImpl.class);
    private final FxDealRepository repository;
    private final FxDealValidationService validator;
    private final FxDealMapper mapper;

    @Override
    public FxDealResponseDTO importDeal(final FxDealCreateRequestDTO dealDto) {
        log.info("Starting import process for deal with ID: [{}] at [{}]", dealDto.id(), LocalDateTime.now());

        ensureDealIdIsUnique(dealDto.id());
        validator.validateCurrencies(dealDto.fromCurrency(), dealDto.toCurrency());
        final var fxDeal = mapper.mapToEntity(dealDto);
        final var savedFxDeal = repository.save(fxDeal);

        log.info("End the importing of the deal with ID: [{}] at [{}]", dealDto.id(), LocalDateTime.now());

        return mapper.mapToResponse(savedFxDeal);
    }

    private void ensureDealIdIsUnique(final String id) {
        if (repository.existsById(id)) {
            log.warn("Duplicate deal ID detected: [{}]", id);
            throw new BusinessViolationException(
                    String.format("Deal with ID [%s] already exists", id)
            );
        }
    }
}
