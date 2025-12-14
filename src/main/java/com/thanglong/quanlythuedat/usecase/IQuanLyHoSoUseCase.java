package com.thanglong.quanlythuedat.usecase;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.HoSoEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NhatKyXuLyEntity;
import com.thanglong.quanlythuedat.usecase.dto.BaoCaoThongKeDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoInputDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoOutputDTO;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface IQuanLyHoSoUseCase {
    // 1. Nghiệp vụ chính
    HoSoOutputDTO nopHoSoKhaiThue(HoSoInputDTO input, MultipartFile file);
    String duyetHoSo(Long maHoSo, boolean dongY, String lyDo);
    void thanhToanThue(Long maHoSo);
    
    // 2. Tra cứu
    HoSoEntity layChiTietHoSo(Long maHoSo);
    List<HoSoEntity> layDanhSachHoSo();
    List<HoSoEntity> layLichSuNopThue(Long maNguoiDung);
    List<NhatKyXuLyEntity> xemLichSuXuLy(Long maHoSo);

    // 3. Báo cáo
    BaoCaoThongKeDTO layBaoCaoThongKe(Integer nam, String khuVuc);
    ByteArrayInputStream xuatBaoCaoExcel();
}