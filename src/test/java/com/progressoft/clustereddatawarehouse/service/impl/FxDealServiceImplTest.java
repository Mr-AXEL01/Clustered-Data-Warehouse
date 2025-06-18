package com.progressoft.clustereddatawarehouse.service.impl;

import com.progressoft.clustereddatawarehouse.domain.FxDeal;
import com.progressoft.clustereddatawarehouse.dto.FxDealCreateRequestDTO;
import com.progressoft.clustereddatawarehouse.dto.FxDealResponseDTO;
import com.progressoft.clustereddatawarehouse.exception.BusinessViolationException;
import com.progressoft.clustereddatawarehouse.mapper.FxDealMapper;
import com.progressoft.clustereddatawarehouse.repository.FxDealRepository;
import com.progressoft.clustereddatawarehouse.service.FxDealValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FxDealServiceImplTest {

    @Mock
    private FxDealRepository repository;

    @Mock
    private FxDealValidationService validator;

    @Mock
    private FxDealMapper mapper;

    @InjectMocks
    private FxDealServiceImpl service;

    private FxDealCreateRequestDTO createDTO;
    private FxDeal fxDealEntity;
    private FxDeal savedFxDeal;
    private FxDealResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        createDTO = new FxDealCreateRequestDTO("deal_1", "USD", "EUR", BigDecimal.valueOf(100.0) , Instant.now());
        fxDealEntity = new FxDeal("deal_1", "USD", "EUR", BigDecimal.valueOf(100.0) , createDTO.timestamp());
        savedFxDeal = new FxDeal("deal_1", "USD", "EUR", BigDecimal.valueOf(100.0) , createDTO.timestamp());
        responseDTO = new FxDealResponseDTO("deal_1", "USD", "EUR", BigDecimal.valueOf(100.0) , createDTO.timestamp());
    }

    @Test
    void givenValidDeal_whenImportDeal_thenReturnResponseDTO() {
        when(repository.existsById(createDTO.id())).thenReturn(false);
        doNothing().when(validator).validateCurrencies(createDTO.fromCurrency(), createDTO.toCurrency());
        when(mapper.mapToEntity(createDTO)).thenReturn(fxDealEntity);
        when(repository.save(fxDealEntity)).thenReturn(savedFxDeal);
        when(mapper.mapToResponse(savedFxDeal)).thenReturn(responseDTO);

        FxDealResponseDTO result = service.importDeal(createDTO);

        assertThat(result).isEqualTo(responseDTO);

        verify(repository).existsById(createDTO.id());
        verify(validator).validateCurrencies("USD", "EUR");
        verify(repository).save(fxDealEntity);
        verify(mapper).mapToEntity(createDTO);
        verify(mapper).mapToResponse(savedFxDeal);
    }

    @Test
    void givenDuplicateDealId_whenImportDeal_thenThrowBusinessViolationException() {
        when(repository.existsById(createDTO.id())).thenReturn(true);

        assertThatThrownBy(() -> service.importDeal(createDTO))
                .isInstanceOf(BusinessViolationException.class)
                .hasMessageContaining("Deal with ID [deal_1] already exists");

        verify(repository).existsById(createDTO.id());
        verifyNoMoreInteractions(repository, validator, mapper);
    }
}