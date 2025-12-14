package com.thanglong.quanlythuedat.infrastructure.repository.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "BangGiaDat")
@Data
public class BangGiaDatEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maBangGia")
    private Integer maBangGia;

    @Column(name = "maKhuVuc")
    private Integer maKhuVuc;

    @Column(name = "maLoaiDat")
    private Integer maLoaiDat;

    @Column(name = "donGiaM2")
    private Double donGiaM2;

    @Column(name = "ngayBanHanh")
    private LocalDate ngayBanHanh;

    @Column(name = "ngayHetHieuLuc")
    private LocalDate ngayHetHieuLuc;

    @Column(name = "soCongVanQuyDinh")
    private String soCongVanQuyDinh;

    @Column(name = "trangThai")
    private String trangThai;
}