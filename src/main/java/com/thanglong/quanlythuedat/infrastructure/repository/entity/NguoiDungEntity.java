package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "nguoi_dung")
@Data
public class NguoiDungEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Trong SQL là id, giữ nguyên

    @Column(unique = true, nullable = false, name = "ten_dang_nhap") 
    private String tenDangNhap;

    @Column(nullable = false, name = "mat_khau") 
    private String matKhau;
    
    @Column(name = "ho_ten")
    private String hoTen;

    // [FIX] Map biến 'soDinhDanh' vào cột 'cccd' trong SQL
    @Column(unique = true, nullable = false, name = "cccd")
    private String soDinhDanh; 

    @Column(name = "loai_doi_tuong")
    private Integer loaiDoiTuong; 
    
    @Column(name = "quoc_tich")
    private String quocTich = "Vietnam"; 

    @Column(name = "vai_tro")
    private String vaiTro; 
    
    @Column(name = "dia_chi")
    private String diaChi;
    
    private String sdt;
    private String email;
    
    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();
    
    @Column(name = "anh_giay_to")
    private String anhGiayTo; 
    
    // [FIX QUAN TRỌNG] Map biến 'trangThai' vào cột 'hoat_dong' trong SQL
    @Column(name = "hoat_dong", columnDefinition = "tinyint(1) default 1")
    private Boolean trangThai = true; 
}