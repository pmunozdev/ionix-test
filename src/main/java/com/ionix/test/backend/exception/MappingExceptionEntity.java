package com.ionix.test.backend.exception;

public class MappingExceptionEntity extends RuntimeException {

    public MappingExceptionEntity(String message){
        super(message);
    }

    public MappingExceptionEntity(String message, Throwable err){
        super(message, err);
    }
}
