package com.thanglong.quanlythuedat.domain.model;

import lombok.Data;

@Data
public class BangGiaDat {
    private Long maBangGia;
    private Integer namApDung;
    private String tenLoaiDat;
    private Double donGiaM2; // VD: 10.000.000 VNĐ
    private Double thueSuat; // VD: 0.03% (0.0003)
}