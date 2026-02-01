package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "land_price_frames")
@Data
public class LandPriceFrameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fiscal_year")
    private Integer fiscalYear; // Land price changes annually
    
    @Column(name = "zone_area")
    private String zoneArea;     // Location (e.g., District 1, County A)
    
    @Column(name = "land_type_code")
    private String landTypeCode;  // Reference to land type (stored as string code)
    
    @Column(name = "price_per_square_meter")
    private Double pricePerSquareMeter;   // Price per m2
}
