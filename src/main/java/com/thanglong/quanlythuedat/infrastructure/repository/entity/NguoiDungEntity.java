package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "nguoi_dung")
@Data
public class NguoiDungEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maNguoiDung; // Đổi id thành maNguoiDung cho khớp UML

    @Column(unique = true, nullable = false) private String tenDangNhap;
    @Column(nullable = false) private String matKhau;
    private String hoTen;

    @Column(unique = true, nullable = false)
    private String soDinhDanh; // Thay cho cccd cũ, map với VNeID

    private Integer loaiDoiTuong; // 1=VN, 2=NN, 3=ToChuc
    private String quocTich = "Vietnam"; 

    private String vaiTro; // ADMIN, CAN_BO, CHU_DAT (Thay cho bảng VaiTro để đơn giản hóa)
    
    private String diaChi;
    private String sdt;
    private String email;
    private LocalDateTime ngayTao = LocalDateTime.now();
    
    private String anhGiayTo; // Giữ lại từ version trước
    
    @Column(columnDefinition = "boolean default true")
    private Boolean trangThai = true; // Đổi hoatDong thành trangThai cho khớp UML
}