package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "ho_so_khai_thue")
@Data
public class HoSoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long maNguoiKhai;
    private Long maThuaDat;
    private Integer namKhaiThue;
    private Double dienTichKhaiBao;
    private String mucDichSuDung;
    private Double soTienThue;
    private LocalDateTime ngayNop;
    private String trangThai;
    private String ghiChu;
}