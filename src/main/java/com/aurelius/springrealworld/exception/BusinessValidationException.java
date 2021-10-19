package com.aurelius.springrealworld.exception;

public class BusinessValidationException extends RuntimeException{
    public BusinessValidationException(String message) {
        super(message);
    }
}
