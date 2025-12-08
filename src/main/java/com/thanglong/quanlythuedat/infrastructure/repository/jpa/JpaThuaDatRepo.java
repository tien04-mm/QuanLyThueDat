package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.ThuaDatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Nhớ import List

@Repository
public interface JpaThuaDatRepo extends JpaRepository<ThuaDatEntity, Long> {
    // [MỚI] Tìm danh sách đất của một người
    List<ThuaDatEntity> findByMaChuSoHuu(Long maChuSoHuu);
}