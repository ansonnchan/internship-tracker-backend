package com.anson.internshiptracker.repository;


import com.anson.internshiptracker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // This sets up in-memory database for testing
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void testSaveUser() {
        // Create user
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        
        // Save user
        User saved = userRepository.save(user);
        
        // Verify
        assertNotNull(saved.getId());
        assertEquals("Test User", saved.getName());
    }
    
    @Test
    public void testFindByEmail() {
        // Create and save user
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");
        userRepository.save(user);
        
        // Find by email
        Optional<User> found = userRepository.findByEmail("john@example.com");
        
        // Verify
        assertTrue(found.isPresent());
        assertEquals("John Doe", found.get().getName());
    }
    
    @Test
    public void testFindByEmailNotFound() {
        Optional<User> found = userRepository.findByEmail("nonexistent@example.com");
        assertFalse(found.isPresent());
    }
}