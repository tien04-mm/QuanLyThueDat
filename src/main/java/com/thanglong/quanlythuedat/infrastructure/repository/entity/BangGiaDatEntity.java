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

    private String loaiDat;   // Map với loai_dat_quy_hoach bên ThuaDat
    private Double donGiaM2;
    private Double thueSuat;
}