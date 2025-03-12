package com.sit.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "ait_student_enquiries")
@Data
@ToString(exclude = "enquries") // Prevents infinite loop in toString()
public class StudentEnqEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENQUIRY_ID")
    private Integer enqId;

    @Column(name = "STUDENT_NAME", nullable = false, length = 50)
    private String studentName;

    @Column(name = "PHNO", nullable = false, length = 10)
    private Long phoneNumber; 

    @Column(name = "CLASS_MODE", nullable = false, length = 20)
    private String classMode;

    @Column(name = "COURSE_NAME", nullable = false, length = 50)
    private String courseName;

    @Column(name = "ENQUIRY_STATUS", nullable = false, length = 20)
    private String enquiryStatus;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDate createdDate;

    @Column(name = "UPDATED_DATE", nullable = false)
    private LocalDate updatedDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false) // Standardized column name
    private UserDetailsEntity user;

    // Automatically set created and updated date
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
        this.updatedDate = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDate.now();
    }
    
    @Override
    public String toString() {
        return "StudentEnqEntity{" +
                "enquiryId=" + enqId +
                ", studentName='" + studentName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", enquiryStatus='" + enquiryStatus + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", userId=" + (user != null ? user.getUserId() : null) +  // Avoid direct reference
                '}';
    }

}
