package com.thanglong.quanlythuedat.usecase;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface IAdminUseCase {
    
    BangGiaDatEntity capNhatBangGiaDat(BangGiaDatEntity bangGiaMoi);

    NguoiDungEntity taoTaiKhoanCanBo(NguoiDungEntity canBoMoi);

    String importDuLieuDatDai(MultipartFile file) throws IOException;

    // Các hàm xóa cũ
    void xoaNguoiDung(Long id);
    void xoaThuaDat(Long id);

    // [MỚI] Hàm khóa tài khoản (Thay vì xóa vĩnh viễn)
    void khoaTaiKhoan(Long id);
}