package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "land_types")
@Data
public class LandTypeEntity {
    @Id
    @Column(name = "type_code")
    private String typeCode; // PK: ODT, ONT, CLN (Manually entered, not auto-generated)

    @Column(name = "type_name")
    private String typeName; // Urban land, Agricultural land, etc.
    
    @Column(name = "tax_rate")
    private Double taxRate;   // Tax percentage (e.g., 0.0003)
}
