package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.usecase.IPropertyDeclarationUseCase;
import com.thanglong.quanlythuedat.usecase.dto.ReportStatisticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private IPropertyDeclarationUseCase propertyDeclarationUseCase;

    // API: GET /api/reports/statistics
    // Function: Tax officer/Admin views dashboard statistics
    @GetMapping("/statistics")
    public ResponseEntity<ReportStatisticsDTO> getStatisticsReport() {
        ReportStatisticsDTO report = propertyDeclarationUseCase.getStatisticsReport();
        return ResponseEntity.ok(report);
    }
}
