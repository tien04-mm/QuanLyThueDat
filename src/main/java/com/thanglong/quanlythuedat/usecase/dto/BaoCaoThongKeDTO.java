package com.thanglong.quanlythuedat.usecase.dto;

import lombok.Data;

@Data
public class BaoCaoThongKeDTO {
    private Integer nam;                // Năm báo cáo
    private Integer tongSoHoSo;         // Tổng số hồ sơ
    
    // --- SỐ LIỆU TÀI CHÍNH ---
    private Double tongThuThue;         // Tiền thực tế đã thu (Đã nộp tiền)
    private Double tongNoThue;          // Tiền đang nợ (Đã duyệt nhưng chưa nộp)
    private Double tongTienPhaiThu;     // Tổng dự kiến (Thu + Nợ)

    // --- SỐ LIỆU SỐ LƯỢNG ---
    private Integer soHoSoChoDuyet;     // Đang chờ xử lý
    
    private Integer soHoSoDaDuyet;      // Đã duyệt (Đồng nghĩa với ĐANG NỢ)
    private Integer soHoSoHoanThanh;    // Đã nộp tiền xong
    private Integer soHoSoBiTuChoi;     // Bị từ chối
    
    private Integer soHoSoGianLan;      // Có dấu hiệu sai lệch
}