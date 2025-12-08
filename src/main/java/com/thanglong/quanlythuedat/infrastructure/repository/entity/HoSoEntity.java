package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "ho_so_khai_thue")
@Data
public class HoSoEntity {
    
    // [CẬP NHẬT] Đổi id thành maHoSo để khớp tài liệu
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_ho_so") // Tên cột trong MySQL sẽ là 'ma_ho_so'
    private Long maHoSo;

    @Column(name = "ma_nguoi_khai")
    private Long maNguoiKhai;

    @Column(name = "ma_thua_dat")
    private Long maThuaDat;

    private Integer namKhaiThue;
    private Double dienTichKhaiBao;
    private String mucDichSuDung;
    private Double soTienThue;
    private LocalDateTime ngayNop;
    private String trangThai;
    
    @Column(length = 1000) // Cho phép ghi chú dài
    private String ghiChu; // Mapping với GhiChuCanBo trong tài liệu

    // [MỚI] Hàm tiện ích để tạo mã hiển thị đẹp (VD: HS2025-005)
    // Frontend có thể gọi getter này để lấy mã đẹp
    public String getMaHienThi() {
        return "HS" + namKhaiThue + "-" + String.format("%03d", maHoSo);
    }
}