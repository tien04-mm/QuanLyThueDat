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
        // [SỬA LỖI QUAN TRỌNG]: 
        // Thay vì tìm theo mỗi loaiDat, phải tìm chính xác theo 3 tiêu chí: Năm + Khu Vực + Mã Loại Đất
        // Vì giá đất mỗi năm và mỗi khu vực là khác nhau.
        
        Optional<BangGiaDatEntity> existing = bangGiaDatRepo.findByNamApDungAndKhuVucAndMaLoaiDat(
                bangGiaMoi.getNamApDung(),
                bangGiaMoi.getKhuVuc(),
                bangGiaMoi.getMaLoaiDat()
        );
        
        if (existing.isPresent()) {
            // Nếu đã có giá cho Năm/Khu vực/Loại đất này rồi -> Cập nhật giá tiền mới
            BangGiaDatEntity bangGiaCu = existing.get();
            bangGiaCu.setDonGiaM2(bangGiaMoi.getDonGiaM2());
            
            // Lưu ý: Thuế suất giờ nằm ở bảng LoaiDat, bảng BangGiaDat chỉ quản lý Đơn giá
            return bangGiaDatRepo.save(bangGiaCu);
        } else {
            // Nếu chưa có (ví dụ năm mới hoặc khu vực mới) -> Thêm mới
            return bangGiaDatRepo.save(bangGiaMoi);
        }
    }

    // --- CHỨC NĂNG 2: QUẢN LÝ NGƯỜI DÙNG (Tạo Cán bộ thuế) ---
    @Override
    public NguoiDungEntity taoTaiKhoanCanBo(NguoiDungEntity canBoMoi) {
        // Kiểm tra trùng tên đăng nhập
        if (nguoiDungRepo.existsByTenDangNhap(canBoMoi.getTenDangNhap())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        
        // Cưỡng chế vai trò là Cán bộ thuế
        canBoMoi.setVaiTro("CAN_BO");
        
        // Mật khẩu demo mặc định (nếu Admin không nhập)
        if (canBoMoi.getMatKhau() == null || canBoMoi.getMatKhau().isEmpty()) {
            canBoMoi.setMatKhau("123456"); 
        }
        
        return nguoiDungRepo.save(canBoMoi);
    }
}