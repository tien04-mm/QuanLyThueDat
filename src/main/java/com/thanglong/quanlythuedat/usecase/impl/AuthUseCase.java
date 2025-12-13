package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.JpaNguoiDungRepo;
import com.thanglong.quanlythuedat.usecase.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AuthUseCase {

    @Autowired private JpaNguoiDungRepo nguoiDungRepo;

    public NguoiDungEntity dangNhap(LoginDTO loginRequest) {
        NguoiDungEntity user = nguoiDungRepo.findByTenDangNhapAndMatKhau(loginRequest.getUsername(), loginRequest.getPassword())
                .orElseThrow(() -> new RuntimeException("Sai tài khoản hoặc mật khẩu!"));
        
        // [DEBUG] In ra xem nó đang là true hay false hay null
        System.out.println("User: " + user.getTenDangNhap());
        System.out.println("Trạng thái (trangThai): " + user.getTrangThai());

        // Kiểm tra trạng thái
        if (Boolean.FALSE.equals(user.getTrangThai())) {
            throw new RuntimeException("Tài khoản đang bị khóa (trangThai = false/null)!");
        }
        return user;
    }

    public NguoiDungEntity dangKy(NguoiDungEntity nguoiMoi, MultipartFile file) {
        if (nguoiDungRepo.existsByTenDangNhap(nguoiMoi.getTenDangNhap())) 
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        
        // Sửa: Kiểm tra trùng số định danh (thay vì cccd)
        if (nguoiDungRepo.existsBySoDinhDanh(nguoiMoi.getSoDinhDanh())) 
            throw new RuntimeException("Số định danh (CCCD) đã tồn tại!");

        if (file != null && !file.isEmpty()) {
            nguoiMoi.setAnhGiayTo("giayto_" + System.currentTimeMillis() + "_" + file.getOriginalFilename());
        }

        nguoiMoi.setVaiTro("CHU_DAT");
        nguoiMoi.setTrangThai(false); // Chờ duyệt
        return nguoiDungRepo.save(nguoiMoi);
    }
}