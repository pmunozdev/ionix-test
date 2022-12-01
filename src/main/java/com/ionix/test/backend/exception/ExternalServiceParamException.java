package com.ionix.test.backend.exception;

public class ExternalServiceParamException extends RuntimeException {

    public ExternalServiceParamException(String message){
        super(message);
    }

    public ExternalServiceParamException(String message, Throwable err){
        super(message, err);
    }

}
