package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.HoSoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaHoSoRepo extends JpaRepository<HoSoEntity, Long> {
    // Các hàm find mặc định của JPA đã đủ dùng
}