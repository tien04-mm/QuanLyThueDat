package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.HoSoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface JpaHoSoRepo extends JpaRepository<HoSoEntity, Long> {
    
    // Tìm hồ sơ theo mã người khai (map vào cột maChuDat trong DB mới)
    List<HoSoEntity> findByMaChuDat(Long maChuDat);

    // [BỔ SUNG QUAN TRỌNG] Hàm tìm hồ sơ theo năm (Lấy năm từ cột ngayTao)
    // Fix lỗi: The method findByNam(int) is undefined
    @Query("SELECT h FROM HoSoEntity h WHERE YEAR(h.ngayTao) = :nam")
    List<HoSoEntity> findByNam(@Param("nam") Integer nam);
}