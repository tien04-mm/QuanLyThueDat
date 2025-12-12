package com.thanglong.quanlythuedat.usecase.dto;
import lombok.Data;

@Data
public class HoSoOutputDTO {
    private Long maHoSo;
    private Double soTienPhaiNop; // Sửa tên
    private Double tongGiaTriDat; // Thêm mới
    private String trangThai;
    private Boolean dauHieuGianLan; // Thêm mới
    private String thongBao;
}