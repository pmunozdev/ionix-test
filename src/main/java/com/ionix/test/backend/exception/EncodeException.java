package com.ionix.test.backend.exception;

public class EncodeException extends RuntimeException {

    public EncodeException(String message){
        super(message);
    }

    public EncodeException(String message, Throwable err){
        super(message, err);
    }

}
