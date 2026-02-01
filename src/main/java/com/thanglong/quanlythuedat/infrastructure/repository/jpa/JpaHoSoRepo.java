package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.PropertyDeclarationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaHoSoRepo extends JpaRepository<PropertyDeclarationEntity, Long> {
    // Custom query methods can be added here as needed
}