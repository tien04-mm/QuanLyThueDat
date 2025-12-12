package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "khieu_nai")
@Data
public class KhieuNaiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long maHoSo;

    @Column(nullable = false)
    private Long maNguoiGui;
    
    @Column(length = 2000, nullable = false)
    private String noiDung;
    
    private LocalDateTime ngayGui = LocalDateTime.now(); // Mặc định lấy giờ hiện tại

    @Column(nullable = false)
    private String trangThai = "CHO_XU_LY"; // Mặc định

    private String phanHoiCuaCanBo;
}