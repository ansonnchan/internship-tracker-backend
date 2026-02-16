package com.anson.internshiptracker.controller;

import com.anson.internshiptracker.controller.UserController.LoginRequest;
import com.anson.internshiptracker.service.UserService;

public class AuthController {
    private final UserService userService;
    
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        if (userService.validateLogin(request.getEmail(), request.getPassword())) {
            //TODO: generate JWT token later
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse)
        }
    }
}
