package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "thua_dat") // Tên bảng trong MySQL
@Data
public class ThuaDatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Map với maThuaDat

    private String soTo;
    private String soThua;
    private String diaChi;
    private Double dienTichGoc;
    private String loaiDatQuyHoach;
    private Long maChuSoHuu;
}