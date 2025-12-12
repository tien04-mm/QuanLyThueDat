package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.ThuaDatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaThuaDatRepo extends JpaRepository<ThuaDatEntity, Long> {
    
    // Tìm danh sách đất của một chủ sở hữu (API /cua-toi)
    List<ThuaDatEntity> findByMaChuSoHuu(Long maChuSoHuu);

    // Tìm chính xác thửa đất theo bản đồ (API /tra-cuu)
    Optional<ThuaDatEntity> findBySoToAndSoThua(String soTo, String soThua);
}