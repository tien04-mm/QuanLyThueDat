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

    private String tenDangNhap;
    private String matKhau;
    private String hoTen;
    private String cccd;
    private String vaiTro; // ADMIN, CAN_BO, CHU_DAT
}