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

    @Column(nullable = false) private Long maNguoiKhai;
    @Column(nullable = false) private Long maThuaDat;
    @Column(nullable = false) private Integer namKhaiThue;
    @Column(nullable = false) private Double dienTichKhaiBao;
    @Column(nullable = false) private String mucDichSuDung;

    private String fileDinhKem; // File upload
    private Double soTienThue;
    private LocalDateTime ngayNop;
    @Column(nullable = false) private String trangThai;
    
    @Column(length = 1000) private String ghiChu; 
    @Column(length = 2000) private String lichSuXuLy; // Log lịch sử

    public String getMaHienThi() {
        return "HS" + namKhaiThue + "-" + String.format("%03d", maHoSo);
    }
}