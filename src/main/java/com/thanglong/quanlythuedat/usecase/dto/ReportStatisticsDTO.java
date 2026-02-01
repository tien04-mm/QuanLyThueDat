package com.thanglong.quanlythuedat.usecase.dto;

import lombok.Data;

@Data
public class ReportStatisticsDTO {
    private long totalDeclarations;
    private double totalEstimatedTaxAmount;
    
    // Detailed statistics by status
    private long declarationsPendingApproval;
    private long declarationsApproved;
    private long declarationsRejected;
    private long declarationsFlaggedForFraud; // Important: Fraud detection flag
}