package com.thanglong.quanlythuedat.usecase;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;

public interface IAdminUseCase {
    
    // Admin thêm mới hoặc cập nhật giá đất
    BangGiaDatEntity capNhatBangGiaDat(BangGiaDatEntity bangGiaMoi);

    // Admin tạo tài khoản cho Cán bộ thuế mới
    NguoiDungEntity taoTaiKhoanCanBo(NguoiDungEntity canBoMoi);
}