package com.thanglong.quanlythuedat.usecase.dto;

import lombok.Data;

@Data
public class BaoCaoThongKeDTO {
    private long tongSoHoSo;
    
    // [MỚI] Tổng tiền đã thu thực tế (Trạng thái HOAN_THANH)
    private double tongThuThue; 
    
    // [MỚI] Tổng tiền nợ (Trạng thái DA_DUYET nhưng chưa đóng)
    private double tongNoThue;
    
    // Số liệu đếm
    private long soHoSoChoDuyet;
    private long soHoSoDaDuyet;
    private long soHoSoBiTuChoi;
    private long soHoSoGianLan; 
}