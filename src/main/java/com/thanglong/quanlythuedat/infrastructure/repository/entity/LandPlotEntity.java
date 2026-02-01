package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "land_plots")
@Data
public class LandPlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Land record information
    @Column(name = "map_sheet_number")
    private String mapSheetNumber;

    @Column(name = "plot_number")
    private String plotNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "zone_area") 
    private String zoneArea; // For reference pricing (e.g., District A)
    
    @Column(name = "original_area")
    private Double originalArea; // Original measured area for fraud prevention
    
    @Column(name = "land_type_code")
    private String landTypeCode;   // Land use classification (e.g., ODT, CLN)
    
    @Column(name = "owner_id")
    private Long ownerId;    // ID of the User (land owner)
}