package com.progressoft.clustereddatawarehouse.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;

public record FxDealCreateRequestDTO(

        @NotBlank
        String id,

        @NotBlank @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid ISO 4217 code (e.g., USD, EUR)")
        String fromCurrency,

        @NotBlank @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid ISO 4217 code (e.g., USD, EUR)")
        String toCurrency,

        @NotNull @Positive
        BigDecimal amount,

        @NotNull @PastOrPresent
        Instant timestamp
) {
}
