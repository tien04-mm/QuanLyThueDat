package com.thanglong.quanlythuedat.usecase.dto;

import lombok.Data;

@Data
public class PropertyDeclarationOutputDTO {
    private Long declarationId;
    private Double taxAmount;
    private String status;
    private String message;
}