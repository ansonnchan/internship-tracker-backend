package com.anson.internshiptracker.exception;

public class UnauthorizedException extends ApiException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
