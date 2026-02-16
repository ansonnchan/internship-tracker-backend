package com.anson.internshiptracker.service;

import com.anson.internshiptracker.exception.ResourceNotFoundException;
import com.anson.internshiptracker.exception.UnauthorizedException;
import com.anson.internshiptracker.model.Application;
import com.anson.internshiptracker.model.ApplicationStatus;
import com.anson.internshiptracker.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ApplicationService {
    
    private final ApplicationRepository applicationRepository;
    
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    
    // Create new application
    public Application createApplication(Application application) {
        application.setStatus(ApplicationStatus.APPLIED);
        application.setDateApplied(LocalDate.now());
        return applicationRepository.save(application);
    }
    
    // Get all applications for a specific user
    public List<Application> getAllApplicationsByUser(Long userId) {
        return applicationRepository.findByUserId(userId);
    }
    
    // Get all applications 
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
    
    // Get application by ID 
    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Application", id));
    }
    
    // Get application by ID with user verification
    public Application getApplicationByIdAndUser(Long id, Long userId) {
        Application application = getApplicationById(id);
        
        // verify the application belongs to the user
        if (!application.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You don't have permission to access this application");
        }
        
        return application;
    }
    
    // Update application with user verification
    public Application updateApplication(Long id, Application updatedApp, Long userId) {
        Application existingApp = getApplicationByIdAndUser(id, userId);
        
        
        existingApp.setCompany(updatedApp.getCompany());
        existingApp.setPosition(updatedApp.getPosition());
        existingApp.setStatus(updatedApp.getStatus());
        existingApp.setDateApplied(updatedApp.getDateApplied());
        
        return applicationRepository.save(existingApp);
    }
    
    // Delete application with user verification
    public void deleteApplication(Long id, Long userId) {
        Application application = getApplicationByIdAndUser(id, userId);
        applicationRepository.delete(application);
    }
    
    // Update status only 
    public Application updateStatus(Long id, ApplicationStatus status, Long userId) {
        Application application = getApplicationByIdAndUser(id, userId);
        application.setStatus(status);
        return applicationRepository.save(application);
    }
}
