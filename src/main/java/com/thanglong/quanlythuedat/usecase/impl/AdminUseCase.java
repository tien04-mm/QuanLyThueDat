package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.JpaBangGiaDatRepo;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.JpaNguoiDungRepo;
import com.thanglong.quanlythuedat.usecase.IAdminUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminUseCase implements IAdminUseCase {

    @Autowired
    private JpaBangGiaDatRepo bangGiaDatRepo;

    @Autowired
    private JpaNguoiDungRepo nguoiDungRepo;

    // --- CHỨC NĂNG 1: QUẢN LÝ BẢNG GIÁ (Admin cập nhật giá đất) ---
    @Override
    public BangGiaDatEntity capNhatBangGiaDat(BangGiaDatEntity bangGiaMoi) {
        // Kiểm tra xem loại đất này đã có trong DB chưa
        Optional<BangGiaDatEntity> existing = bangGiaDatRepo.findByLoaiDat(bangGiaMoi.getLoaiDat());
        
        if (existing.isPresent()) {
            // Nếu có rồi thì cập nhật giá mới
            BangGiaDatEntity bangGiaCu = existing.get();
            bangGiaCu.setDonGiaM2(bangGiaMoi.getDonGiaM2());
            bangGiaCu.setThueSuat(bangGiaMoi.getThueSuat());
            return bangGiaDatRepo.save(bangGiaCu);
        } else {
            // Nếu chưa có thì thêm mới
            return bangGiaDatRepo.save(bangGiaMoi);
        }
    }

    // --- CHỨC NĂNG 2: QUẢN LÝ NGƯỜI DÙNG (Tạo Cán bộ thuế) ---
    @Override
    public NguoiDungEntity taoTaiKhoanCanBo(NguoiDungEntity canBoMoi) {
        if (nguoiDungRepo.existsByTenDangNhap(canBoMoi.getTenDangNhap())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        // Cưỡng chế vai trò là Cán bộ thuế
        canBoMoi.setVaiTro("CAN_BO");
        // Mật khẩu demo mặc định (thực tế cần mã hóa)
        if (canBoMoi.getMatKhau() == null) {
            canBoMoi.setMatKhau("123456"); 
        }
        return nguoiDungRepo.save(canBoMoi);
    }
}