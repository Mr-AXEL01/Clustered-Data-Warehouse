package com.progressoft.clustereddatawarehouse.service;

import com.progressoft.clustereddatawarehouse.dto.FxDealCreateRequestDTO;
import com.progressoft.clustereddatawarehouse.dto.FxDealResponseDTO;

public interface FxDealService {

    FxDealResponseDTO importDeal(FxDealCreateRequestDTO dealDto);
}
