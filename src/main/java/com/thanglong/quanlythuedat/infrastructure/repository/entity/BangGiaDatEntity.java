package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bang_gia_dat")
@Data
public class BangGiaDatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer namApDung; // Giá đất thay đổi theo năm
    private String khuVuc;     // Vị trí (VD: Quan 1, Huyen A...)
    
    private String maLoaiDat;  // Liên kết với bảng LoaiDat (Lưu mã string cho đơn giản)
    private Double donGiaM2;   // Giá tiền 1m2
}