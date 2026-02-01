package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.LandPlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaThuaDatRepo extends JpaRepository<LandPlotEntity, Long> {
    
    List<LandPlotEntity> findByOwnerId(Long ownerId);
}