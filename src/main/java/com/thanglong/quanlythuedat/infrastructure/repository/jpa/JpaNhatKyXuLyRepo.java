package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.NhatKyXuLyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaNhatKyXuLyRepo extends JpaRepository<NhatKyXuLyEntity, Long> {
    List<NhatKyXuLyEntity> findByMaHoSo(Long maHoSo);
}