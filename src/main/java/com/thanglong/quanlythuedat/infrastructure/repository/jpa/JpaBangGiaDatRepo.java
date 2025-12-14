package com.thanglong.quanlythuedat.infrastructure.repository.jpa;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaBangGiaDatRepo extends JpaRepository<BangGiaDatEntity, Integer> {
    // Tìm theo ID Khu Vực và ID Loại Đất
    Optional<BangGiaDatEntity> findByMaKhuVucAndMaLoaiDatAndTrangThai(Integer maKhuVuc, Integer maLoaiDat, String trangThai);
}