package com.thanglong.quanlythuedat.domain.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HoSoKhaiThue {
    // Thuộc tính bám sát ERD "Hồ sơ khai thuế"
    private Long maHoSo;
    private Long maNguoiKhai;
    private Long maThuaDat;
    private Integer namKhaiThue;
    
    // Dữ liệu người dân tự nhập (Input)
    private Double dienTichKhaiBao;
    private String mucDichSuDungKhaiBao;
    
    // Dữ liệu hệ thống tính toán (Output)
    private Double soTienThue;
    private LocalDateTime ngayNop;
    
    // Trạng thái: CHO_DUYET, DA_DUYET, CANH_BAO_GIAN_LAN, TU_CHOI
    private String trangThai; 
    private String ghiChuCanBo; // Lý do từ chối/cảnh báo
    
    // Logic nghiệp vụ nằm ngay trong Domain Model
    public boolean isGianLanDienTich(Double dienTichGoc) {
        // Logic: Nếu diện tích khai báo nhỏ hơn diện tích gốc quá 2% -> Gian lận
        return this.dienTichKhaiBao < (dienTichGoc * 0.98);
    }
}