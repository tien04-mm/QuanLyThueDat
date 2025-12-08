package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JpaNguoiDungRepo extends JpaRepository<NguoiDungEntity, Long> {
    // Tìm người dùng để đăng nhập
    Optional<NguoiDungEntity> findByTenDangNhapAndMatKhau(String tenDangNhap, String matKhau);
    
    // Kiểm tra trùng tên đăng nhập khi đăng ký
    boolean existsByTenDangNhap(String tenDangNhap);
}