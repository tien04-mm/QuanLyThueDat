package com.thanglong.quanlythuedat.infrastructure.repository.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "LichSuSoHuu")
@Data
public class LichSuSoHuuEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maLichSu")
    private Long maLichSu;

    @Column(name = "maThuaDat")
    private Long maThuaDat;

    @Column(name = "maChuDat")
    private Long maChuDat;

    @Column(name = "ngayBatDau")
    private LocalDateTime ngayBatDau;

    @Column(name = "ngayKetThuc")
    private LocalDateTime ngayKetThuc;

    @Column(name = "trangThai")
    private String trangThai;

    @Column(name = "hinhThucSoHuu")
    private String hinhThucSoHuu;
}