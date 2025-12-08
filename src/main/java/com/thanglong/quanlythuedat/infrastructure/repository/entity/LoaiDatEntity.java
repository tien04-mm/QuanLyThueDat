package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "loai_dat")
@Data
public class LoaiDatEntity {
    @Id
    private String maLoaiDat; // PK: ODT, ONT, CLN (Nhập tay, không tự tăng)

    private String tenLoaiDat; // Đất ở đô thị, Đất trồng cây...
    private Double thueSuat;   // % Thuế (VD: 0.0003)
}