package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "nguoi_dung")
@Data
public class NguoiDungEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tenDangNhap;
    
    private String matKhau;
    private String hoTen;
    
    @Column(unique = true)
    private String cccd; // Dùng để định danh pháp lý
    
    private String vaiTro; // ADMIN, CAN_BO, CHU_DAT

    // Thông tin liên hệ
    private String diaChi;
    private String sdt;
    private String email;

    // [MỚI] Trạng thái: true = Hoạt động, false = Bị khóa
    // Mặc định khi tạo mới là true
    @Column(columnDefinition = "boolean default true")
    private Boolean hoatDong = true;
}