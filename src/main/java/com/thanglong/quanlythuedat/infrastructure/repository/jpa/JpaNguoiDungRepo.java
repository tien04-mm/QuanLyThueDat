package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JpaNguoiDungRepo extends JpaRepository<NguoiDungEntity, Long> {
    
    // Tìm để đăng nhập
    Optional<NguoiDungEntity> findByTenDangNhapAndMatKhau(String tenDangNhap, String matKhau);
    
    // Check trùng tên đăng nhập
    boolean existsByTenDangNhap(String tenDangNhap);

    // [MỚI] Check trùng CCCD (Khớp Activity Đăng ký)
    boolean existsByCccd(String cccd);
}