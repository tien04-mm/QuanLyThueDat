package com.thanglong.quanlythuedat.usecase.dto;

import lombok.Data;

@Data
public class BaoCaoThongKeDTO {
    private long tongSoHoSo;
    private double tongTienThueDuKien;
    
    // Thống kê chi tiết theo trạng thái
    private long soHoSoChoDuyet;
    private long soHoSoDaDuyet;
    private long soHoSoBiTuChoi;
    private long soHoSoGianLan; // Quan trọng
}