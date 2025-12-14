package com.thanglong.quanlythuedat.infrastructure.repository.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "NhatKyXuLy")
@Data
public class NhatKyXuLyEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maNhatKy")
    private Long maNhatKy;

    @Column(name = "maHoSo")
    private Long maHoSo;

    @Column(name = "maCanBo")
    private Long maCanBo;

    @Column(name = "trangThaiTu")
    private String trangThaiTu;

    @Column(name = "trangThaiDen")
    private String trangThaiDen;

    @Column(name = "ghiChu")
    private String ghiChu;

    @Column(name = "thoiGianXuLy")
    private LocalDateTime thoiGianXuLy;
}