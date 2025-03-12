package com.sit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ait_enquiry_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnqStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_ID")  // Map to existing auto-increment column
    private Long statusId;  

    @Column(name = "status", nullable = false, unique = true)
    private String status;

    @Column(name = "status_name", nullable = true)
    private String statusName;
}
