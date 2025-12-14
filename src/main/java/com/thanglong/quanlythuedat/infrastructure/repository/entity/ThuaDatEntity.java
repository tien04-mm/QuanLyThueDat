package com.thanglong.quanlythuedat.infrastructure.repository.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "ThuaDat")
@Data
public class ThuaDatEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maThuaDat")
    private Long maThuaDat;

    @Column(name = "soTo")
    private String soTo;

    @Column(name = "soThua")
    private String soThua;

    @Column(name = "diaChiChiTiet")
    private String diaChiChiTiet;

    @Column(name = "dienTichGoc")
    private Double dienTichGoc;

    @Column(name = "mucDichSuDung")
    private String mucDichSuDung;

    @Column(name = "maLoaiDat")
    private Integer maLoaiDat; // FK

    @Column(name = "maKhuVuc")
    private Integer maKhuVuc; // FK

    @Column(name = "maChuSoHuu")
    private Long maChuSoHuu; // FK

    @Column(name = "trangThai")
    private String trangThai;

    @Column(name = "ngayTao")
    private LocalDateTime ngayTao;
}