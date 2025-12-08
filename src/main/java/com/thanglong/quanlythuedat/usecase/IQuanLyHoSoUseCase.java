package com.thanglong.quanlythuedat.usecase;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.HoSoEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.ThuaDatEntity;
import com.thanglong.quanlythuedat.usecase.dto.BaoCaoThongKeDTO; // Import mới
import com.thanglong.quanlythuedat.usecase.dto.HoSoInputDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoOutputDTO;

import java.util.List;

public interface IQuanLyHoSoUseCase {
    
    // Nộp hồ sơ
    HoSoOutputDTO nopHoSoKhaiThue(HoSoInputDTO input);

    // Xem danh sách hồ sơ (Cán bộ)
    List<HoSoEntity> layDanhSachHoSo();
    
    // Xem lịch sử nộp (Chủ đất)
    List<HoSoEntity> layLichSuNopThue(Long maNguoiDung);

    // Duyệt hồ sơ
    String duyetHoSo(Long maHoSo, boolean dongY, String lyDo);

    // Tra cứu đất
    List<ThuaDatEntity> traCuuDatCuaToi(Long maChuSoHuu);

    // [MỚI] Xuất báo cáo thống kê
    BaoCaoThongKeDTO layBaoCaoThongKe();
}