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

    private Long maHoSo;      // Khiếu nại cho hồ sơ nào
    private Long maNguoiGui;  // Ai khiếu nại
    
    @Column(length = 1000)
    private String noiDung;   // Giải trình của dân
    
    private LocalDateTime ngayGui;
    private String trangThai; // CHO_XU_LY, DA_XU_LY
    private String phanHoiCuaCanBo;
}