package com.progressoft.clustereddatawarehouse.mapper;

import com.progressoft.clustereddatawarehouse.domain.FxDeal;
import com.progressoft.clustereddatawarehouse.dto.FxDealCreateRequestDTO;
import com.progressoft.clustereddatawarehouse.dto.FxDealResponseDTO;

public class FxDealMapperImpl implements FxDealMapper {
    @Override
    public FxDealResponseDTO mapToResponse(FxDeal deal) {
        return new FxDealResponseDTO(
                deal.getId(),
                deal.getFromCurrency(),
                deal.getToCurrency(),
                deal.getAmount(),
                deal.getTimestamp()
        );
    }

    @Override
    public FxDeal mapToEntity(FxDealCreateRequestDTO request) {
        return new FxDeal(
                request.id(),
                request.fromCurrency(),
                request.toCurrency(),
                request.amount(),
                request.timestamp()
        );
    }
}
