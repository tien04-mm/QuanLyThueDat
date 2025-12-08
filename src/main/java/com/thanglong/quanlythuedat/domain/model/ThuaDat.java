package com.thanglong.quanlythuedat.domain.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThuaDat {
    // Các thuộc tính bám sát ERD "Thửa đất"
    private Long maThuaDat;
    private String soTo;
    private String soThua;
    private String diaChi;
    
    // Quan trọng: Dữ liệu gốc để so sánh gian lận
    private Double dienTichGoc; 
    private String loaiDatQuyHoach; // VD: ODT, ONT
    private Long maChuSoHuu; // ID của Chủ đất
}