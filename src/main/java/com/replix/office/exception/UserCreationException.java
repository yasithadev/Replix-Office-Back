package com.replix.office.exception;

public class UserCreationException extends RuntimeException {
    private final String errorCode;
    private final String details;
    private final Integer errorNumber;

    public UserCreationException(Integer errorNumber,String errorCode,String message, String details) {
        super(message);
        this.details = details;
        this.errorCode = errorCode;
        this.errorNumber = errorNumber;
    }

    public Integer getErrorNumber() {
        return errorNumber;
    }

    public String getDetails() {
        return details;
    }

    public String getErrorCode() {
        return errorCode;
    }
}