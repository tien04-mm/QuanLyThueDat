package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "thua_dat")
@Data
public class ThuaDatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String soTo;

    @Column(nullable = false)
    private String soThua;

    private String diaChi;
    
    @Column(nullable = false)
    private String khuVuc; // Quan trọng để tính giá đất
    
    @Column(nullable = false)
    private Double dienTichGoc; 

    @Column(nullable = false)
    private String maLoaiDat; // ODT, CLN...
    
    private Long maChuSoHuu;  // Có thể null nếu đất công hoặc chưa cấp sổ
}