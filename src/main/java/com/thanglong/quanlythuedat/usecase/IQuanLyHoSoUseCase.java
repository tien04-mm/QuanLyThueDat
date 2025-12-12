package com.thanglong.quanlythuedat.usecase;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.HoSoEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.KhieuNaiEntity;
import com.thanglong.quanlythuedat.usecase.dto.BaoCaoThongKeDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoInputDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoOutputDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface IQuanLyHoSoUseCase {
    // Nghiệp vụ chính
    HoSoOutputDTO nopHoSoKhaiThue(HoSoInputDTO input);
    List<HoSoEntity> layDanhSachHoSo();
    HoSoEntity layChiTietHoSo(Long id); // [MỚI] Xem chi tiết
    String duyetHoSo(Long maHoSo, boolean dongY, String lyDo);
    List<HoSoEntity> layLichSuNopThue(Long maNguoiDung);

    // [MỚI] Thanh toán & Xuất file (Diagram 2 & 6)
    void thanhToanThue(Long maHoSo); 
    ByteArrayInputStream xuatBaoCaoExcel(); // Xuất file Excel

    // Thống kê & Khiếu nại
    BaoCaoThongKeDTO layBaoCaoThongKe();
    KhieuNaiEntity guiKhieuNai(Long maHoSo, Long maNguoiGui, String noiDung);
}