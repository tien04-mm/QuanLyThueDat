package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "thua_dat")
@Data
public class ThuaDatEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maThuaDat; // Đổi id thành maThuaDat

    private String soTo;
    private String soThua;
    private String diaChiChiTiet; // Đổi tên cho khớp UML
    
    @Column(nullable = false) private String maKhuVuc; // Quan trọng để tính hệ số K
    
    private Double dienTichGoc;
    private String maLoaiDat;
    private String mucDichSuDung;
    private Long maChuSoHuu;
    private LocalDateTime ngayTao = LocalDateTime.now();
}