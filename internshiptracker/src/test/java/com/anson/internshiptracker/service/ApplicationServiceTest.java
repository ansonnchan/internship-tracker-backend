package com.anson.internshiptracker.service;

import com.anson.internshiptracker.model.Application;
import com.anson.internshiptracker.repository.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ApplicationServiceTest {
    
    @Mock
    private ApplicationRepository applicationRepository;
    
    @InjectMocks
    private ApplicationService applicationService;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testCreateApplication() {
        // Create test application
        Application app = new Application();
        app.setCompany("Google");
        app.setPosition("SWE Intern");
        app.setStatus("APPLIED");
        app.setDateApplied(LocalDate.now());
        
        // Mock repository behavior
        when(applicationRepository.save(any(Application.class))).thenReturn(app);
        
        // Call service
        Application created = applicationService.createApplication(app);
        
        // Verify
        assertNotNull(created);
        assertEquals("Google", created.getCompany());
        verify(applicationRepository, times(1)).save(app);
    }
    
    @Test
    public void testUpdateStatus() {
        // Create test application
        Application app = new Application();
        app.setId(1L);
        app.setCompany("Amazon");
        app.setStatus("APPLIED");
        
        // Mock repository behavior
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(app));
        when(applicationRepository.save(any(Application.class))).thenAnswer(i -> i.getArguments()[0]);
        
        // Call service
        Application updated = applicationService.updateStatus(1L, "INTERVIEW");
        
        // Verify
        assertEquals("INTERVIEW", updated.getStatus());
        verify(applicationRepository, times(1)).findById(1L);
        verify(applicationRepository, times(1)).save(any(Application.class));
    }
}