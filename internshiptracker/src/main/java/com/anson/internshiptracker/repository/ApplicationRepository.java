package com.anson.internshiptracker.repository;

import com.anson.internshiptracker.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
    // Find all applications for a specific user
    List<Application> findByUserId(Long userId);
}
