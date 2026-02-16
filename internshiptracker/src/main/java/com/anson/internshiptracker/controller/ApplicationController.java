package com.anson.internshiptracker.controller;

import com.anson.internshiptracker.model.Application;
import com.anson.internshiptracker.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    
    //create new application
    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application created = applicationService.createApplication(application);
        return ResponseEntity.ok(created);
    }

    //get all applications
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    //get application by ID
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationbyId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //update application
    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody Application application) {
       try {
        Application updated = applicationService.updateApplication(id, application);
        return ResponseEntity.ok(updated);
       }
       catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
       }
    }

    //delete application
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }

    //Update status only
    @PatchMapping("/{id}/status")
    public ResponseEntity<Application> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Application updated = applicationService.updateStatus(id, status);
            return ResponseEntity.ok(updated);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
