package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.HoSoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaHoSoRepo extends JpaRepository<HoSoEntity, Long> {
    // Có thể thêm các hàm findByMaNguoiKhai... nếu cần query tay
    // Hiện tại dùng findAll().stream().filter() trong UseCase vẫn ổn với dữ liệu nhỏ
}