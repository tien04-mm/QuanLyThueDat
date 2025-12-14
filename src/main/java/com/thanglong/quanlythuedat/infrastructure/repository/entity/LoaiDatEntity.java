package com.thanglong.quanlythuedat.infrastructure.repository.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "LoaiDat")
@Data
public class LoaiDatEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maLoaiDat")
    private Integer maLoaiDat;

    @Column(name = "tenLoaiDat")
    private String tenLoaiDat;

    @Column(name = "moTa")
    private String moTa;

    @Column(name = "thueSuat")
    private Double thueSuat;
}