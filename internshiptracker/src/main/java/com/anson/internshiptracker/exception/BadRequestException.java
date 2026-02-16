package com.anson.internshiptracker.exception;

public class BadRequestException extends ApiException{
    public BadRequestException(String message) {
        super(message);
    }
}