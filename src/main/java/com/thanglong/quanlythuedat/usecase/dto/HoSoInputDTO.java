package com.thanglong.quanlythuedat.usecase.dto;
import lombok.Data;

@Data
public class HoSoInputDTO {
    private Long maNguoiDung; 
    private Long maThuaDat;
    private Double dienTichKhaiBao;
    private String mucDichSuDungKhaiBao; // Sửa tên cho khớp UML
    private Integer namKhaiThue;
}