package com.anson.internshiptracker.exception;

public abstract class ApiException extends RuntimeException {
    protected ApiException (String message) {
        super(message);
    }
    
}
