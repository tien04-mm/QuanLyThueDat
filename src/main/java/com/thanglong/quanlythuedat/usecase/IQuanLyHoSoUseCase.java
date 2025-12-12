package com.thanglong.quanlythuedat.usecase;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.HoSoEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.KhieuNaiEntity;
import com.thanglong.quanlythuedat.usecase.dto.BaoCaoThongKeDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoInputDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoOutputDTO;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface IQuanLyHoSoUseCase {
    HoSoOutputDTO nopHoSoKhaiThue(HoSoInputDTO input, MultipartFile file);
    KhieuNaiEntity guiKhieuNai(KhieuNaiEntity data, MultipartFile file);
    String layLichSuXuLy(Long maHoSo);
    BaoCaoThongKeDTO layBaoCaoThongKe(Integer nam, String khuVuc);
    
    List<HoSoEntity> layDanhSachHoSo();
    HoSoEntity layChiTietHoSo(Long id);
    String duyetHoSo(Long maHoSo, boolean dongY, String lyDo);
    void thanhToanThue(Long maHoSo);
    ByteArrayInputStream xuatBaoCaoExcel();
    List<HoSoEntity> layLichSuNopThue(Long maNguoiDung);
}