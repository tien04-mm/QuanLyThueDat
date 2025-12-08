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

    public NguoiDungEntity dangNhap(LoginDTO loginRequest) {
        NguoiDungEntity user = nguoiDungRepo.findByTenDangNhapAndMatKhau(loginRequest.getUsername(), loginRequest.getPassword())
                .orElseThrow(() -> new RuntimeException("Đăng nhập thất bại: Sai tài khoản hoặc mật khẩu!"));
        
        // [MỚI] Kiểm tra trạng thái Khóa (Theo Activity Diagram)
        if (Boolean.FALSE.equals(user.getHoatDong())) {
            throw new RuntimeException("Tài khoản này đã bị KHÓA do vi phạm quy định!");
        }

        return user;
    }

    public NguoiDungEntity dangKy(NguoiDungEntity nguoiMoi) {
        // Check 1: Trùng tên đăng nhập
        if (nguoiDungRepo.existsByTenDangNhap(nguoiMoi.getTenDangNhap())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        
        // [MỚI] Check 2: Trùng CCCD (Theo Activity Diagram)
        if (nguoiDungRepo.existsByCccd(nguoiMoi.getCccd())) {
            throw new RuntimeException("Số CCCD này đã được đăng ký tài khoản!");
        }

        // Thiết lập mặc định
        nguoiMoi.setVaiTro("CHU_DAT");
        nguoiMoi.setHoatDong(true); // Mặc định là Hoạt động
        
        return nguoiDungRepo.save(nguoiMoi);
    }
}