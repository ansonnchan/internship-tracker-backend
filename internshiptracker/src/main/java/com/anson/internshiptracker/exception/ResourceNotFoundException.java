package com.anson.internshiptracker.exception;

public class ResourceNotFoundException extends ApiException {
    
    public ResourceNotFoundException(String resource, Object identifier) {
        super(resource + " not found with identifier: " + identifier);
    }
}
