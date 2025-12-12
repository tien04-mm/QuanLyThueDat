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
    
    @Column(nullable = false)
    private String matKhau;

    @Column(nullable = false)
    private String hoTen;
    
    @Column(unique = true, nullable = false)
    private String cccd; // Căn cước công dân (Bắt buộc)
    
    @Column(nullable = false)
    private String vaiTro; // ADMIN, CAN_BO, CHU_DAT

    private String diaChi;
    private String sdt;
    private String email;

    @Column(columnDefinition = "boolean default true")
    private Boolean hoatDong = true; // Mặc định là True
}