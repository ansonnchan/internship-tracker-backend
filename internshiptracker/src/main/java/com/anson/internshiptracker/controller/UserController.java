package com.anson.internshiptracker.controller;

import com.anson.internshiptracker.dto.AuthResponse;
import com.anson.internshiptracker.exception.UnauthorizedException;
import com.anson.internshiptracker.model.User;
import com.anson.internshiptracker.service.UserService;
import com.anson.internshiptracker.util.JwtUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    private final JwtUtil jwtUtil;
    
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    
    // Signup 
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        
        User savedUser = userService.registerUser(user);
        
        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser.getEmail());
        
        return ResponseEntity.ok(new AuthResponse(token, savedUser.getEmail(), savedUser.getName()));
    }
    
    // Login 
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        boolean isValid = userService.validateLogin(request.getEmail(), request.getPassword());
        
        if (!isValid) {
            throw new UnauthorizedException("Invalid email or password");
        }
        
        // Get user details
        User user = userService.findByEmail(request.getEmail())
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());
        
        return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), user.getName()));
    }
    
    // Get current user profile (requires authentication)
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        return ResponseEntity.ok(user);
    }
    
    // Get user by email 
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email, Authentication authentication) {
        // Check if requesting own profile or admin (you can add role checking later)
        if (!email.equals(authentication.getName())) {
            throw new UnauthorizedException("You can only view your own profile");
        }
        
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    // DTO for login request
    public static class LoginRequest {
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        private String email;
        @NotBlank(message = "Password is required")
        private String password;
        
        public String getEmail() {
            return email;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
    }
    
    // DTO for signup request
    public static class SignupRequest {
        @NotBlank(message = "Name is required")
        private String name;
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        private String email;
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;
        
        public String getName() {
            return name;
        }
        
        public String getEmail() {
            return email;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
    }
}
