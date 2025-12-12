package com.thanglong.quanlythuedat.usecase;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IAdminUseCase {
    // --- 1. QUẢN LÝ NGƯỜI DÙNG ---
    NguoiDungEntity taoTaiKhoanNhanVien(NguoiDungEntity nhanVien);
    void pheDuyetTaiKhoan(Long maNguoiDung);
    void khoaTaiKhoan(Long maNguoiDung);
    void xoaNguoiDung(Long maNguoiDung);
    NguoiDungEntity capNhatThongTin(Long maNguoiDung, NguoiDungEntity dataMoi);
    List<NguoiDungEntity> timKiemNguoiDung(String vaiTro, String keyword);

    // --- 2. QUẢN LÝ ĐẤT ĐAI ---
    BangGiaDatEntity capNhatBangGiaDat(BangGiaDatEntity bangGia);
    String importDuLieuDatDai(MultipartFile file) throws IOException;
    void xoaThuaDat(Long maThuaDat);
}