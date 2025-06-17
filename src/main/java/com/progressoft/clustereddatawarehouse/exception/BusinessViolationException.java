package com.progressoft.clustereddatawarehouse.exception;

public class BusinessViolationException extends RuntimeException {
    public BusinessViolationException(final String message) {
        super(message);
    }
}
