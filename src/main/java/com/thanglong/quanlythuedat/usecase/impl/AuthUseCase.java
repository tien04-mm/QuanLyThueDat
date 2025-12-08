package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.JpaNguoiDungRepo;
import com.thanglong.quanlythuedat.usecase.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthUseCase {

    @Autowired
    private JpaNguoiDungRepo nguoiDungRepo;

    // Chức năng Đăng nhập
    public NguoiDungEntity dangNhap(LoginDTO loginRequest) {
        // Tìm trong DB xem có ai khớp username và password không
        return nguoiDungRepo.findByTenDangNhapAndMatKhau(loginRequest.getUsername(), loginRequest.getPassword())
                .orElseThrow(() -> new RuntimeException("Đăng nhập thất bại: Sai tài khoản hoặc mật khẩu!"));
    }

    // Chức năng Đăng ký (Dành cho Chủ đất mới)
    public NguoiDungEntity dangKy(NguoiDungEntity nguoiMoi) {
        if (nguoiDungRepo.existsByTenDangNhap(nguoiMoi.getTenDangNhap())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        // Mặc định đăng ký mới là CHU_DAT
        nguoiMoi.setVaiTro("CHU_DAT");
        return nguoiDungRepo.save(nguoiMoi);
    }
}