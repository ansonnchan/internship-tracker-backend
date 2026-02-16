package com.anson.internshiptracker.controller;

import com.anson.internshiptracker.model.ApplicationStatus;
import com.anson.internshiptracker.model.User;
import com.anson.internshiptracker.exception.UnauthorizedException;
import com.anson.internshiptracker.service.EmailService;
import com.anson.internshiptracker.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
public class EmailController {
    
    private final EmailService emailService;
    private final UserService userService;
    
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }
    
    // Classify and update application status 
    @PostMapping("/classify-and-update")
    public ResponseEntity<ClassificationResponse> classifyAndUpdate(
            @Valid @RequestBody EmailClassificationRequest request,
            Authentication authentication) {
        
        String email = authentication.getName();
        User currentUser = userService.findByEmail(email)
            .orElseThrow(() -> new UnauthorizedException("User not found"));
        
        ApplicationStatus status = emailService.classifyAndUpdate(
            request.getApplicationId(),
            request.getEmailSubject(),
            request.getEmailBody(),
            currentUser.getId()
        );
        
        return ResponseEntity.ok(new ClassificationResponse(status));
    }
    
    // Classify only (no update) 
    @PostMapping("/classify")
    public ResponseEntity<ClassificationResponse> classifyOnly(@Valid @RequestBody EmailTextRequest request) {
        ApplicationStatus status = emailService.classifyOnly(request.getSubject(), request.getBody());
        return ResponseEntity.ok(new ClassificationResponse(status));
    }
    
    // Request DTOs
    public static class EmailClassificationRequest {
        @NotNull(message = "Application ID is required")
        private Long applicationId;
        @NotBlank(message = "Email subject is required")
        private String emailSubject;
        @NotBlank(message = "Email body is required")
        private String emailBody;
        
        public Long getApplicationId() {
            return applicationId;
        }
        
        public String getEmailSubject() {
            return emailSubject;
        }
        
        public String getEmailBody() {
            return emailBody;
        }
        
        public void setApplicationId(Long applicationId) {
            this.applicationId = applicationId;
        }
        
        public void setEmailSubject(String emailSubject) {
            this.emailSubject = emailSubject;
        }
        
        public void setEmailBody(String emailBody) {
            this.emailBody = emailBody;
        }
    }
    
    public static class EmailTextRequest {
        @NotBlank(message = "Email subject is required")
        private String subject;
        @NotBlank(message = "Email body is required")
        private String body;
        
        public String getSubject() {
            return subject;
        }
        
        public String getBody() {
            return body;
        }
        
        public void setSubject(String subject) {
            this.subject = subject;
        }
        
        public void setBody(String body) {
            this.body = body;
        }
    }
    
    // Response DTO 
    public static class ClassificationResponse {
        private ApplicationStatus status;
        private String statusName;
        
        public ClassificationResponse(ApplicationStatus status) {
            this.status = status;
            this.statusName = status.name();
        }
        
        public ApplicationStatus getStatus() {
            return status;
        }
        
        public String getStatusName() {
            return statusName;
        }
    }
}
