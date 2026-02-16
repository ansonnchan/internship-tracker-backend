package com.anson.internshiptracker.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "applications")
@Getter
@Setter
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String company;
    private String position;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    
    private LocalDate dateApplied;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id") 
    private User user; 
}
