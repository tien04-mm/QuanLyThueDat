package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "property_declarations")
@Data
public class PropertyDeclarationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "declaration_id")
    private Long declarationId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "land_plot_id")
    private Long landPlotId;

    @Column(name = "fiscal_year")
    private Integer fiscalYear;

    @Column(name = "declared_area")
    private Double declaredArea;

    @Column(name = "usage_purpose")
    private String usagePurpose;

    @Column(name = "tax_amount")
    private Double taxAmount;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "status")
    private String status;
    
    @Column(name = "notes", length = 1000)
    private String notes; // Officer notes and audit trail

    // Utility method for displaying formatted declaration ID (e.g., DECL2025-005)
    public String getDisplayId() {
        return "DECL" + fiscalYear + "-" + String.format("%03d", declarationId);
    }
}