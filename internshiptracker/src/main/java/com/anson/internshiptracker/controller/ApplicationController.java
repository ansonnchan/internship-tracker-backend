package com.anson.internshiptracker.controller;

import com.anson.internshiptracker.model.Application;
import com.anson.internshiptracker.model.ApplicationStatus;
import com.anson.internshiptracker.model.User;
import com.anson.internshiptracker.exception.UnauthorizedException;
import com.anson.internshiptracker.service.ApplicationService;
import com.anson.internshiptracker.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    
    private final ApplicationService applicationService;
    private final UserService userService;
    
    public ApplicationController(ApplicationService applicationService, UserService userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }
    
    // Create new application 
    @PostMapping
    public ResponseEntity<Application> createApplication(
            @Valid @RequestBody Application application,
            Authentication authentication) {
        
        // Get current user from JWT token
        String email = authentication.getName();
        User currentUser = userService.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        // Associate application with user
        application.setUser(currentUser);
        
        Application created = applicationService.createApplication(application);
        return ResponseEntity.ok(created);
    }
    
    // Get all applications for current user
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications(Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        List<Application> applications = applicationService.getAllApplicationsByUser(currentUser.getId());
        return ResponseEntity.ok(applications);
    }
    
    // Get application by ID 
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(
            @PathVariable Long id,
            Authentication authentication) {
        
        String email = authentication.getName();
        User currentUser = userService.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        Application application = applicationService.getApplicationByIdAndUser(id, currentUser.getId());
        return ResponseEntity.ok(application);
    }
    
    // Update application 
    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody Application application,
            Authentication authentication) {
        
        String email = authentication.getName();
        User currentUser = userService.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        Application updated = applicationService.updateApplication(id, application, currentUser.getId());
        return ResponseEntity.ok(updated);
    }
    
    // Delete application
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id,
            Authentication authentication) {
        
        String email = authentication.getName();
        User currentUser = userService.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        applicationService.deleteApplication(id, currentUser.getId());
        return ResponseEntity.noContent().build();
    }
    
    // Update status only 
    @PatchMapping("/{id}/status")
    public ResponseEntity<Application> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest request,
            Authentication authentication) {
        
        String email = authentication.getName();
        User currentUser = userService.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        Application updated = applicationService.updateStatus(id, request.getStatus(), currentUser.getId());
        return ResponseEntity.ok(updated);
    }
    
    // DTO for status update
    public static class StatusUpdateRequest {
        @NotNull(message = "Status is required")
        private ApplicationStatus status;
        
        public ApplicationStatus getStatus() {
            return status;
        }
        
        public void setStatus(ApplicationStatus status) {
            this.status = status;
        }
    }
}
