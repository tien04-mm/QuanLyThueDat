package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "bang_gia_dat")
@Data
public class BangGiaDatEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maBangGia;

    @Column(nullable = false) private String maLoaiDat;
    @Column(nullable = false) private String maKhuVuc; // Link sang KhuVucEntity

    private Integer namApDung; // Vẫn giữ để biết hiệu lực
    private LocalDateTime ngayBanHanh;
    private LocalDateTime ngayHetHieuLuc;
    
    @Column(nullable = false) private Double donGiaM2;
    private String soCongVanQuyDinh;
    private String trangThai;
}