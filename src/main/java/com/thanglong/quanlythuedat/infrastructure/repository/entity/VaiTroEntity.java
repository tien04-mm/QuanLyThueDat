package com.thanglong.quanlythuedat.infrastructure.repository.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "VaiTro")
@Data
public class VaiTroEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maVaiTro")
    private Integer maVaiTro;

    @Column(name = "tenVaiTro")
    private String tenVaiTro;

    @Column(name = "moTa")
    private String moTa;
}