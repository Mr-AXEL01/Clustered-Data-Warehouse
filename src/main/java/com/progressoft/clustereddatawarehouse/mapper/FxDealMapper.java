package com.progressoft.clustereddatawarehouse.mapper;

import com.progressoft.clustereddatawarehouse.domain.FxDeal;
import com.progressoft.clustereddatawarehouse.dto.FxDealCreateRequestDTO;
import com.progressoft.clustereddatawarehouse.dto.FxDealResponseDTO;

public interface FxDealMapper {

    FxDealResponseDTO mapToResponse(final FxDeal deal);

    FxDeal mapToEntity(final FxDealCreateRequestDTO request);
}
