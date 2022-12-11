package com.kasperovich.javafxapp.exception;

public class InvalidOrgTypeException extends Exception{

    public InvalidOrgTypeException() {
    }

    public InvalidOrgTypeException(String message) {
        super(message);
    }

    public InvalidOrgTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOrgTypeException(Throwable cause) {
        super(cause);
    }

    public InvalidOrgTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
