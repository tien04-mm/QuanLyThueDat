package com.thanglong.quanlythuedat.usecase;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.HoSoEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.KhieuNaiEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NhatKyXuLyEntity; // Import quan trọng
import com.thanglong.quanlythuedat.usecase.dto.BaoCaoThongKeDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoInputDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoOutputDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface IQuanLyHoSoUseCase {
    // Nghiệp vụ chính
    HoSoOutputDTO nopHoSoKhaiThue(HoSoInputDTO input, MultipartFile file);
    String duyetHoSo(Long maHoSo, boolean dongY, String lyDo);
    void thanhToanThue(Long maHoSo);
    KhieuNaiEntity guiKhieuNai(KhieuNaiEntity data, MultipartFile file);

    // Tra cứu & Lịch sử
    HoSoEntity layChiTietHoSo(Long maHoSo);
    List<HoSoEntity> layDanhSachHoSo();
    List<HoSoEntity> layLichSuNopThue(Long maNguoiDung);
    
    List<NhatKyXuLyEntity> xemLichSuXuLy(Long maHoSo);

    // Báo cáo thống kê & Xuất Excel
    BaoCaoThongKeDTO layBaoCaoThongKe(Integer nam, String khuVuc);
    
    ByteArrayInputStream xuatBaoCaoExcel();
}