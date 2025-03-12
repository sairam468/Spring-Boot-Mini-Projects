package com.sit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ait_courses")  // Ensure it maps to the correct table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID")  // Map to existing column
    private Long id;

    @Column(name = "course_name", nullable = false, unique = true)  // Map column correctly
    private String courseName;

    @Column(name = "description", length = 500)  // Match with DB schema
    private String description;
}
