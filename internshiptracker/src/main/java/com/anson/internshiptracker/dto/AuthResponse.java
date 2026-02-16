package com.anson.internshiptracker.dto;

public class AuthResponse {
    
    private String token;
    private String email;
    private String name;
    private String message;
    
    // Constructor for successful authentication
    public AuthResponse(String token, String email, String name) {
        this.token = token;
        this.email = email;
        this.name = name;
        this.message = "Authentication successful";
    }
    
    // Constructor for error messages
    public AuthResponse(String message) {
        this.message = message;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
