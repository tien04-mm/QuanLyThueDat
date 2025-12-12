package com.thanglong.quanlythuedat.infrastructure.repository.jpa;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaNguoiDungRepo extends JpaRepository<NguoiDungEntity, Long> {
    Optional<NguoiDungEntity> findByTenDangNhap(String tenDangNhap);
    Optional<NguoiDungEntity> findByTenDangNhapAndMatKhau(String tenDangNhap, String matKhau);
    boolean existsByTenDangNhap(String tenDangNhap);
    boolean existsBySoDinhDanh(String soDinhDanh); // Cập nhật tên hàm
}