package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "nhat_ky_xu_ly")
@Data
public class NhatKyXuLyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maNhatKy;

    @Column(nullable = false)
    private Long maHoSo;

    private Long maCanBo; // Người xử lý (có thể null nếu là hệ thống tự động)

    private String trangThaiTu;
    private String trangThaiDen;
    private String ghiChu;
    
    private LocalDateTime thoiGianXuLy = LocalDateTime.now();
}