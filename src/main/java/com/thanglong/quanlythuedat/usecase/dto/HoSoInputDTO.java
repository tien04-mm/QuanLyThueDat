package com.thanglong.quanlythuedat.usecase.dto;

import lombok.Data;

@Data
public class HoSoInputDTO {
    private Long maNguoiDung; // Ai nộp?
    private Long maThuaDat;   // Nộp cho đất nào?
    private Double dienTichKhaiBao; // Người dân nhập
    private String mucDichSuDung;   // Người dân nhập
    private Integer namKhaiThue;
}