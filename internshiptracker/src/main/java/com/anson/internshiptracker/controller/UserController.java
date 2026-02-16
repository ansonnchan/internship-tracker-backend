package com.anson.internshiptracker.controller;

import com.anson.internshiptracker.model.User;
import com.anson.internshiptracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    //Signup 
    @PostMapping("/signup")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        boolean isValid = userService.validateLogin(request.getEmail(), request.getPassword());
        if (isValid) {
            //TODO: return JWT token later
            return ResponseEntity.ok("Login successful");
        }
        else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    //get user by email
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    //DTO for login request
    public static class LoginRequest {
        private String email;
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

    //DTO for signup request
    public static class SignupRequest {
        private String name;
        private String email;
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

    //signup
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
    
}