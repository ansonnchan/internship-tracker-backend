package com.anson.internshiptracker.repository;

import com.anson.internshiptracker.model.Application;
import com.anson.internshiptracker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ApplicationRepositoryTest {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void testSaveApplication() {
        // Create user first
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        User savedUser = userRepository.save(user);
        
        // Create application
        Application app = new Application();
        app.setCompany("Google");
        app.setPosition("Software Engineer Intern");
        app.setStatus("APPLIED");
        app.setDateApplied(LocalDate.now());
        app.setUser(savedUser);
        
        // Save
        Application saved = applicationRepository.save(app);
        
        // Verify
        assertNotNull(saved.getId());
        assertEquals("Google", saved.getCompany());
        assertEquals(savedUser.getId(), saved.getUser().getId());
    }
    
    @Test
    public void testFindAll() {
        // Create user
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        User savedUser = userRepository.save(user);
        
        // Create multiple applications
        Application app1 = new Application();
        app1.setCompany("Google");
        app1.setPosition("SWE Intern");
        app1.setStatus("APPLIED");
        app1.setDateApplied(LocalDate.now());
        app1.setUser(savedUser);
        applicationRepository.save(app1);
        
        Application app2 = new Application();
        app2.setCompany("Amazon");
        app2.setPosition("Software Dev Intern");
        app2.setStatus("INTERVIEW");
        app2.setDateApplied(LocalDate.now());
        app2.setUser(savedUser);
        applicationRepository.save(app2);
        
        // Find all
        List<Application> apps = applicationRepository.findAll();
        
        // Verify
        assertEquals(2, apps.size());
    }
}