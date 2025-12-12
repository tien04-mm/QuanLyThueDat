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
        if (Boolean.FALSE.equals(user.getHoatDong())) {
            throw new RuntimeException("Tài khoản chưa được duyệt hoặc bị khóa!");
        }
        return user;
    }

    // [CẬP NHẬT] Đăng ký nhận file ảnh
    public NguoiDungEntity dangKy(NguoiDungEntity nguoiMoi, MultipartFile file) {
        if (nguoiDungRepo.existsByTenDangNhap(nguoiMoi.getTenDangNhap())) 
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        if (nguoiDungRepo.existsByCccd(nguoiMoi.getCccd())) 
            throw new RuntimeException("CCCD đã tồn tại!");

        // Lưu file ảnh giấy tờ (Logic đơn giản: Lưu tên file)
        if (file != null && !file.isEmpty()) {
            String fileName = "giayto_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            // TODO: Nếu muốn lưu thật thì dùng FileOutputStream để ghi ra ổ cứng
            nguoiMoi.setAnhGiayTo(fileName);
        }

        nguoiMoi.setVaiTro("CHU_DAT");
        nguoiMoi.setHoatDong(false); // Đăng ký xong phải chờ Admin duyệt
        return nguoiDungRepo.save(nguoiMoi);
    }
}