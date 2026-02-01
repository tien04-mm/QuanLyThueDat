package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.LandPriceFrameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaBangGiaDatRepo extends JpaRepository<LandPriceFrameEntity, Long> {
    
    // Spring Data JPA will automatically generate SQL from the method name
    Optional<LandPriceFrameEntity> findByFiscalYearAndZoneAreaAndLandTypeCode(Integer fiscalYear, String zoneArea, String landTypeCode);
}