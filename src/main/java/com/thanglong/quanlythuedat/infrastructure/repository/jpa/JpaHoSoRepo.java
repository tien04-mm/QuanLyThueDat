package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.HoSoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaHoSoRepo extends JpaRepository<HoSoEntity, Long> {
    // Tìm hồ sơ theo mã người khai (map vào cột maChuDat trong DB mới)
    List<HoSoEntity> findByMaChuDat(Long maChuDat);
}