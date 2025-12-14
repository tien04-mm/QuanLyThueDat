package com.thanglong.quanlythuedat.infrastructure.repository.jpa;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.LoaiDatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLoaiDatRepo extends JpaRepository<LoaiDatEntity, Integer> {
}