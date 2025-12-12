package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "khu_vuc")
@Data
public class KhuVucEntity {
    @Id
    @Column(length = 20)
    private String maKhuVuc; // VD: KV_CG_01

    @Column(nullable = false)
    private String tenKhuVuc;

    private String moTa;

    @Column(nullable = false)
    private Double heSoK; // Hệ số điều chỉnh (VD: 1.2)
}