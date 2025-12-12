package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bang_gia_dat")
@Data
public class BangGiaDatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer namApDung; 

    @Column(nullable = false)
    private String khuVuc; 
    
    @Column(nullable = false)
    private String maLoaiDat; 
    
    @Column(nullable = false)
    private Double donGiaM2; 
}