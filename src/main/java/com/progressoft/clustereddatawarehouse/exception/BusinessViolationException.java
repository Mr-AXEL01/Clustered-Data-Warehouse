package com.progressoft.clustereddatawarehouse.exception;

public class BusinessViolationException extends RuntimeException {
    public BusinessViolationException(String message) {
        super(message);
    }
}
