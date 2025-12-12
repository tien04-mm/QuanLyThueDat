package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "ho_so_khai_thue")
@Data
public class HoSoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_ho_so")
    private Long maHoSo;

    @Column(nullable = false) private Long maNguoiKhai; // map với maNguoiDung
    @Column(nullable = false) private Long maThuaDat;
    
    private Integer namKhaiThue;
    private Double dienTichKhaiBao;
    private String mucDichSuDungKhaiBao; // Khớp UML

    // Các trường tính toán
    private Double tongGiaTriDat;
    private Double soTienPhaiNop; // Thay cho soTienThue
    
    private Boolean dauHieuGianLan; // Thay cho text logic cũ
    private String trangThai; // CHO_DUYET...
    
    private String fileDinhKemGiaoDich; // Khớp UML
    private LocalDateTime ngayNop = LocalDateTime.now();
    private LocalDateTime ngayDuyet;
    
    public String getMaHienThi() {
        return "HS" + namKhaiThue + "-" + String.format("%03d", maHoSo);
    }
}