package com.thanglong.quanlythuedat.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "thua_dat")
@Data
public class ThuaDatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Thông tin pháp lý sổ đỏ
    private String soTo;
    private String soThua;
    private String diaChi;
    private String khuVuc; // Để tra bảng giá (VD: Huyen A)
    
    private Double dienTichGoc; // "Sự thật" để chống gian lận
    private String maLoaiDat;   // Quy hoạch là đất gì (ODT, CLN...)
    
    private Long maChuSoHuu;    // ID của NguoiDung
}