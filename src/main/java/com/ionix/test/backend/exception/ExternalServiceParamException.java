package com.ionix.test.backend.exception;

public class ExternalServiceException extends RuntimeException {

    public ExternalServiceException(String message){
        super(message);
    }

    public ExternalServiceException(String message, Throwable err){
        super(message, err);
    }

}
