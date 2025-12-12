package com.thanglong.quanlythuedat.usecase;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.ThuaDatEntity;
import java.util.List;

public interface IThuaDatUseCase {
    List<ThuaDatEntity> traCuuDatCuaToi(Long maChuSoHuu);
    ThuaDatEntity timThuaDat(String soTo, String soThua);
    
    // Admin dùng: Cập nhật thông tin đất thủ công (Screenshot 3)
    ThuaDatEntity capNhatThongTinDat(Long id, ThuaDatEntity dataMoi);
}