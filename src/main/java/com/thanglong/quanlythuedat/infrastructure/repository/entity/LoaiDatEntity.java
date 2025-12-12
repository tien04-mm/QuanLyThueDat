package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "loai_dat")
@Data
public class LoaiDatEntity {
    @Id
    @Column(length = 20)
    private String maLoaiDat; // PK: ODT, ONT...

    @Column(nullable = false)
    private String tenLoaiDat; 

    @Column(nullable = false)
    private Double thueSuat; // Bắt buộc có thuế suất
}