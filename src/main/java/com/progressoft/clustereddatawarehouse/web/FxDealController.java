package com.progressoft.clustereddatawarehouse.web;

import com.progressoft.clustereddatawarehouse.dto.FxDealCreateRequestDTO;
import com.progressoft.clustereddatawarehouse.dto.FxDealResponseDTO;
import com.progressoft.clustereddatawarehouse.service.FxDealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(FxDealController.CONTROLLER_PATH)
@RequiredArgsConstructor
public class FxDealController {

    static final String CONTROLLER_PATH = "/api/v1/fx-deals";

    private final FxDealService service;

    @PostMapping
    public ResponseEntity<FxDealResponseDTO> importFxDeal(@RequestBody @Valid FxDealCreateRequestDTO dealDto) {
        FxDealResponseDTO fxDeal = service.importDeal(dealDto);
        return new ResponseEntity<>(fxDeal, HttpStatus.CREATED);
    }
}
