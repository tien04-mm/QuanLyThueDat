package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaBangGiaDatRepo extends JpaRepository<BangGiaDatEntity, Long> {
    
    // Tìm giá đất cụ thể theo: Năm + Khu Vực + Loại Đất
    Optional<BangGiaDatEntity> findByNamApDungAndKhuVucAndMaLoaiDat(
            Integer namApDung, 
            String khuVuc, 
            String maLoaiDat
    );
}