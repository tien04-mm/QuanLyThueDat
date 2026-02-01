package com.thanglong.quanlythuedat.usecase;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.PropertyDeclarationEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.LandPlotEntity;
import com.thanglong.quanlythuedat.usecase.dto.ReportStatisticsDTO;
import com.thanglong.quanlythuedat.usecase.dto.PropertyDeclarationInputDTO;
import com.thanglong.quanlythuedat.usecase.dto.PropertyDeclarationOutputDTO;

import java.util.List;

public interface IPropertyDeclarationUseCase {
    
    // Submit property declaration
    PropertyDeclarationOutputDTO submitPropertyDeclaration(PropertyDeclarationInputDTO input);

    // Get list of declarations (Officer)
    List<PropertyDeclarationEntity> getAllDeclarations();
    
    // Get submission history (Land Owner)
    List<PropertyDeclarationEntity> getUserDeclarationHistory(Long userId);

    // Review/approve property declaration
    String approveDeclaration(Long declarationId, boolean isApproved, String reason);

    // Lookup user's land plots
    List<LandPlotEntity> getUserLandPlots(Long ownerId);

    // [NEW] Export statistics report
    ReportStatisticsDTO getStatisticsReport();
}