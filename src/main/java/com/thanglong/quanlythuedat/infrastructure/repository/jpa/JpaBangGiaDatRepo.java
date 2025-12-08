package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JpaBangGiaDatRepo extends JpaRepository<BangGiaDatEntity, Long> {
    // Hàm tìm giá đất theo Loại đất (VD: Tìm giá của đất ODT)
    Optional<BangGiaDatEntity> findByLoaiDat(String loaiDat);
}