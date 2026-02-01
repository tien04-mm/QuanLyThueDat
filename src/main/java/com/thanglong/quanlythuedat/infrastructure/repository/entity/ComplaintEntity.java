package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
@Data
public class ComplaintEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "declaration_id")
    private Long declarationId;      // Which declaration is being complained about
    
    @Column(name = "user_id")
    private Long userId;  // Who filed the complaint
    
    @Column(name = "content", length = 1000)
    private String content;   // Complaint details from citizen
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "status")
    private String status; // PENDING_REVIEW, RESOLVED
    
    @Column(name = "officer_response")
    private String officerResponse;
}