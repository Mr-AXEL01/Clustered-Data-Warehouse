package com.progressoft.clustereddatawarehouse.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        int statusCode,
        LocalDateTime timestamp,
        String message,
        String description,
        Object errors
) {}
