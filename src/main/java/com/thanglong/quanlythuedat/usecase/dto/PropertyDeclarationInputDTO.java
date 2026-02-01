package com.thanglong.quanlythuedat.usecase.dto;

import lombok.Data;

@Data
public class PropertyDeclarationInputDTO {
    private Long userId; // Who is submitting the declaration?
    private Long landPlotId;   // For which land plot?
    private Double declaredArea; // User's declared area
    private String usagePurpose;   // Land use purpose
    private Integer fiscalYear;
}