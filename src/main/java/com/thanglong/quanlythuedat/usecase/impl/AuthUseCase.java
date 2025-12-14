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

    public NguoiDungEntity dangNhap(LoginDTO req) {
        NguoiDungEntity user = nguoiDungRepo.findByTenDangNhapAndMatKhau(req.getUsername(), req.getPassword())
                .orElseThrow(() -> new RuntimeException("Sai thông tin!"));
        if (Boolean.FALSE.equals(user.getTrangThai())) throw new RuntimeException("Bị khóa!");
        return user;
    }

    public NguoiDungEntity dangKy(NguoiDungEntity user, MultipartFile file) {
        if(nguoiDungRepo.existsByTenDangNhap(user.getTenDangNhap())) throw new RuntimeException("Trùng user");
        if(nguoiDungRepo.existsBySoDinhDanh(user.getSoDinhDanh())) throw new RuntimeException("Trùng CCCD");
        
        user.setMaVaiTro(4); // 4 = Chủ Đất (theo SQL bạn nhập)
        user.setTrangThai(false);
        return nguoiDungRepo.save(user);
    }
}