package com.thanglong.quanlythuedat.usecase;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IAdminUseCase {
    // Nhóm Đất đai
    BangGiaDatEntity capNhatBangGiaDat(BangGiaDatEntity bangGia);
    String importDuLieuDatDai(MultipartFile file) throws IOException;
    void xoaThuaDat(Long id);

    // Nhóm Người dùng (Cũ)
    NguoiDungEntity taoTaiKhoanNhanVien(NguoiDungEntity nhanVien);
    void khoaTaiKhoan(Long id);
    void xoaNguoiDung(Long id);

    // [MỚI] Các hàm bổ sung để sửa lỗi AdminController
    List<NguoiDungEntity> timKiemNguoiDung(String vaiTro, String keyword);
    NguoiDungEntity capNhatThongTin(Long id, NguoiDungEntity dataMoi);
    void pheDuyetTaiKhoan(Long id);
}