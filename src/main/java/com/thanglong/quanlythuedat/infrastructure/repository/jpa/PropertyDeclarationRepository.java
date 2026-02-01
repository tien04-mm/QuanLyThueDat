package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.PropertyDeclarationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyDeclarationRepository extends JpaRepository<PropertyDeclarationEntity, Long> {
    // Find declarations by user ID
    List<PropertyDeclarationEntity> findByUserId(Long userId);
    
    // Find declarations by land plot ID
    List<PropertyDeclarationEntity> findByLandPlotId(Long landPlotId);
    
    // Find declarations by fiscal year
    List<PropertyDeclarationEntity> findByFiscalYear(Integer fiscalYear);
}
