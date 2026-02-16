package com.anson.internshiptracker.controller;

import com.anson.internshiptracker.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
@CrossOrigin(origins = "*")
public class EmailController {
    
    @Autowired
    private EmailService emailService;

    //classify and update application status
    @PostMapping("/classify-and-update")
    public ResponseEntity<ClassificationResponse> classifyAndUpdate(@RequestBody EmailClassificationRequest request) {
        String status = emailService.classifyAndUpdate(request.getApplicationId(), request.getEmailSubject(), request.getEmailBody());
        return ResponseEntity.ok(new ClassificationResponse(status));
}
//
    //classify only
    @PostMapping("/classify")
    public ResponseEntity<ClassificationResponse> classifyOnly(@RequestBody EmailTextRequest request) {
        String status = emailService.classifyOnly(request.getSubject(), request.getBody());
        return ResponseEntity.ok(new ClassificationResponse(status));
    }

    //Request DTOs
    public static class EmailClassificationRequest {
        private Long applicationId;
        private String emailSubject;
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
    }
    public static class EmailTextRequest {
        private String subject;
        private String body;
        
        public String getSubject() {
            return subject;
        }
        public String getBody() {
            return body;
        }
    }
    public static class ClassificationResponse {
        private String status;
        
        public ClassificationResponse(String status) {
            this.status = status;
        }
        public String getStatus() {
            return status;
        }
    }
}
