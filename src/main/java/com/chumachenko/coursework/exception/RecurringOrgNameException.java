package com.chumachenko.coursework.exception;

public class RecurringOrgNameException extends Exception{
    private String customMessage;

    private Integer errorCode;

    public RecurringOrgNameException(String customMessage, Integer errorCode) {
        this.customMessage = customMessage;
        this.errorCode = errorCode;
    }

    public RecurringOrgNameException(String message, String customMessage, Integer errorCode) {
        super(message);
        this.customMessage = customMessage;
        this.errorCode = errorCode;
    }

    public RecurringOrgNameException(String message, Throwable cause, String customMessage, Integer errorCode) {
        super(message, cause);
        this.customMessage = customMessage;
        this.errorCode = errorCode;
    }

    public RecurringOrgNameException(Throwable cause, String customMessage, Integer errorCode) {
        super(cause);
        this.customMessage = customMessage;
        this.errorCode = errorCode;
    }

    public RecurringOrgNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String customMessage, Integer errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.customMessage = customMessage;
        this.errorCode = errorCode;
    }
}
