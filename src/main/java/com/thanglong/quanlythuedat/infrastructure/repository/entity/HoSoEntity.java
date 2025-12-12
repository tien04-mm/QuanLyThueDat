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
    @Column(name = "ma_ho_so") 
    private Long maHoSo; // Lưu ý: Lombok sẽ sinh ra getMaHoSo()

    @Column(nullable = false)
    private Long maNguoiKhai;

    @Column(nullable = false)
    private Long maThuaDat;

    @Column(nullable = false)
    private Integer namKhaiThue;

    @Column(nullable = false)
    private Double dienTichKhaiBao;

    @Column(nullable = false)
    private String mucDichSuDung;

    private Double soTienThue;
    
    private LocalDateTime ngayNop;
    
    @Column(nullable = false)
    private String trangThai; // CHO_DUYET, CANH_BAO_GIAN_LAN...
    
    @Column(length = 1000)
    private String ghiChu; 

    // Helper method cho Frontend
    public String getMaHienThi() {
        return "HS" + namKhaiThue + "-" + String.format("%03d", maHoSo);
    }
}