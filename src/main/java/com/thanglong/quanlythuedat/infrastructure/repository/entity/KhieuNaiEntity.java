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

    @Column(nullable = false) private Long maHoSo;
    @Column(nullable = false) private Long maNguoiGui;
    
    @Column(length = 2000, nullable = false)
    private String noiDung;
    
    // [MỚI] Lưu tên file/ảnh minh chứng khiếu nại
    private String fileMinhChung;

    private LocalDateTime ngayGui = LocalDateTime.now();

    @Column(nullable = false)
    private String trangThai = "CHO_XU_LY"; 

    private String phanHoiCuaCanBo;
}