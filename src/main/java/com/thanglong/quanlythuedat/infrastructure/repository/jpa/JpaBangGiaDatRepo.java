package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaBangGiaDatRepo extends JpaRepository<BangGiaDatEntity, Long> {
    
    // [QUAN TRỌNG] Hàm này phải khớp tên biến trong UseCase
    Optional<BangGiaDatEntity> findByNamApDungAndMaKhuVucAndMaLoaiDat(
            Integer namApDung, 
            String maKhuVuc, 
            String maLoaiDat
    );
}