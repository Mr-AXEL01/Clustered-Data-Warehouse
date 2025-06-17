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

@RequiredArgsConstructor
public class FxDealServiceImpl implements FxDealService {

    private final FxDealRepository repository;
    private final FxDealValidationService validator;
    private final FxDealMapper mapper;

    // TODO: dont forget logs

    @Override
    public FxDealResponseDTO importDeal(FxDealCreateRequestDTO dealDto) {
        ensureDealIdIsUnique(dealDto.id());
        validator.validateCurrencies(dealDto.fromCurrency(), dealDto.toCurrency());
        FxDeal fxDeal = mapper.mapToEntity(dealDto);
        FxDeal savedFxDeal = repository.save(fxDeal);
        return mapper.mapToResponse(savedFxDeal);
    }

    private void ensureDealIdIsUnique(String id) {
        if(repository.existsById(id)) {
           throw new BusinessViolationException(
                   String.format("Deal with id [%s] already exists", id)
           );
        }
    }
}
