package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.*;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.*;
import com.thanglong.quanlythuedat.usecase.IPropertyDeclarationUseCase;
import com.thanglong.quanlythuedat.usecase.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyDeclarationService implements IPropertyDeclarationUseCase {

    @Autowired private LandPlotRepository landPlotRepository;
    @Autowired private PropertyDeclarationRepository propertyDeclarationRepository;
    @Autowired private JpaBangGiaDatRepo landPriceFrameRepository;
    @Autowired private JpaLoaiDatRepo landTypeRepository;
    @Autowired private JpaKhieuNaiRepo complaintRepository;

    @Override
    public PropertyDeclarationOutputDTO submitPropertyDeclaration(PropertyDeclarationInputDTO input) {
        LandPlotEntity originalLandPlot = landPlotRepository.findById(input.getLandPlotId())
                .orElseThrow(() -> new RuntimeException("Land plot not found!"));

        String status = "PENDING_REVIEW";
        StringBuilder warnings = new StringBuilder();

        // Check for area fraud detection
        if (input.getDeclaredArea() < originalLandPlot.getOriginalArea() * 0.98) {
            status = "FRAUD_SUSPECTED";
            warnings.append("Declared area is significantly lower than original area. ");
        }

        // Check for land use mismatch
        if (!input.getUsagePurpose().equalsIgnoreCase(originalLandPlot.getLandTypeCode())) {
            status = "FRAUD_SUSPECTED";
            warnings.append("Land use purpose does not match zoning classification (" + originalLandPlot.getLandTypeCode() + "). ");
        }

        // Lookup land type for tax rate
        LandTypeEntity landType = landTypeRepository.findById(originalLandPlot.getLandTypeCode())
                .orElseThrow(() -> new RuntimeException("Error: Land type not found in master data!"));
        
        // Lookup pricing frame
        LandPriceFrameEntity priceFrame = landPriceFrameRepository.findByFiscalYearAndZoneAreaAndLandTypeCode(
                input.getFiscalYear(), 
                originalLandPlot.getZoneArea(), 
                originalLandPlot.getLandTypeCode()
        ).orElseThrow(() -> new RuntimeException("No pricing frame available for fiscal year " + input.getFiscalYear() + " in " + originalLandPlot.getZoneArea()));

        double calculatedTaxAmount = input.getDeclaredArea() * priceFrame.getPricePerSquareMeter() * landType.getTaxRate();

        PropertyDeclarationEntity declaration = new PropertyDeclarationEntity();
        declaration.setUserId(input.getUserId());
        declaration.setLandPlotId(input.getLandPlotId());
        declaration.setFiscalYear(input.getFiscalYear());
        declaration.setDeclaredArea(input.getDeclaredArea());
        declaration.setUsagePurpose(input.getUsagePurpose());
        declaration.setTaxAmount(calculatedTaxAmount);
        declaration.setCreatedDate(LocalDateTime.now());
        declaration.setStatus(status);
        declaration.setNotes(warnings.toString());

        propertyDeclarationRepository.save(declaration);

        PropertyDeclarationOutputDTO output = new PropertyDeclarationOutputDTO();
        output.setDeclarationId(declaration.getDeclarationId()); 
        output.setTaxAmount(calculatedTaxAmount);
        output.setStatus(status);
        output.setMessage(warnings.length() > 0 ? warnings.toString() : "Property declaration submitted successfully!");
        
        return output;
    }

    @Override
    public List<PropertyDeclarationEntity> getAllDeclarations() { 
        return propertyDeclarationRepository.findAll(); 
    }
    
    @Override
    public String approveDeclaration(Long declarationId, boolean isApproved, String reason) {
        PropertyDeclarationEntity declaration = propertyDeclarationRepository.findById(declarationId)
                .orElseThrow(() -> new RuntimeException("Declaration not found!"));
        declaration.setStatus(isApproved ? "APPROVED" : "REJECTED");
        declaration.setNotes(reason);
        propertyDeclarationRepository.save(declaration);
        return "Decision recorded";
    }
    
    @Override
    public List<LandPlotEntity> getUserLandPlots(Long ownerId) {
        return landPlotRepository.findByOwnerId(ownerId);
    }
    
    @Override
    public List<PropertyDeclarationEntity> getUserDeclarationHistory(Long userId) {
         return propertyDeclarationRepository.findByUserId(userId);
    }

    @Override
    public ReportStatisticsDTO getStatisticsReport() {
        List<PropertyDeclarationEntity> allDeclarations = propertyDeclarationRepository.findAll();
        ReportStatisticsDTO report = new ReportStatisticsDTO();
        report.setTotalDeclarations(allDeclarations.size());
        double totalTax = allDeclarations.stream().mapToDouble(PropertyDeclarationEntity::getTaxAmount).sum();
        report.setTotalEstimatedTaxAmount(totalTax);
        
        report.setDeclarationsPendingApproval(allDeclarations.stream().filter(d -> "PENDING_REVIEW".equals(d.getStatus())).count());
        report.setDeclarationsApproved(allDeclarations.stream().filter(d -> "APPROVED".equals(d.getStatus())).count());
        report.setDeclarationsRejected(allDeclarations.stream().filter(d -> "REJECTED".equals(d.getStatus())).count());
        report.setDeclarationsFlaggedForFraud(allDeclarations.stream().filter(d -> "FRAUD_SUSPECTED".equals(d.getStatus())).count());

        return report;
    }
    
    // Complaint submission method
    public ComplaintEntity submitComplaint(Long declarationId, Long userId, String content) {
        ComplaintEntity complaint = new ComplaintEntity();
        complaint.setDeclarationId(declarationId);
        complaint.setUserId(userId);
        complaint.setContent(content);
        complaint.setCreatedDate(LocalDateTime.now());
        complaint.setStatus("PENDING_REVIEW");
        return complaintRepository.save(complaint);
    }
}
