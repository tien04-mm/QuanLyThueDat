package com.thanglong.quanlythuedat.infrastructure.repository.jpa;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaNguoiDungRepo extends JpaRepository<NguoiDungEntity, Long> {
    
    // Tìm user để kiểm tra tồn tại (cho chức năng đăng ký)
    Optional<NguoiDungEntity> findByTenDangNhap(String tenDangNhap);

    // [BỔ SUNG DÒNG NÀY ĐỂ SỬA LỖI AUTH USECASE]
    // Tìm khớp cả Tên đăng nhập VÀ Mật khẩu
    Optional<NguoiDungEntity> findByTenDangNhapAndMatKhau(String tenDangNhap, String matKhau);
    
    // Kiểm tra trùng lặp
    boolean existsByTenDangNhap(String tenDangNhap);
    boolean existsByCccd(String cccd);
}