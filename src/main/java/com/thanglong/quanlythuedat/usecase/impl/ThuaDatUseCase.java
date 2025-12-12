package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.ThuaDatEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.JpaThuaDatRepo;
import com.thanglong.quanlythuedat.usecase.IThuaDatUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThuaDatUseCase implements IThuaDatUseCase {

    @Autowired
    private JpaThuaDatRepo thuaDatRepo;

    @Override
    public List<ThuaDatEntity> traCuuDatCuaToi(Long maChuSoHuu) {
        // Bây giờ JpaRepo đã có hàm này, code sẽ hết lỗi
        return thuaDatRepo.findByMaChuSoHuu(maChuSoHuu);
    }

    @Override
    public ThuaDatEntity timThuaDat(String soTo, String soThua) {
        // Hàm này cũng đã được khai báo trong JpaRepo
        return thuaDatRepo.findBySoToAndSoThua(soTo, soThua)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dữ liệu đất với Số tờ/Số thửa này!"));
    }

    @Override
    public ThuaDatEntity capNhatThongTinDat(Long id, ThuaDatEntity dataMoi) {
        ThuaDatEntity dat = thuaDatRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thửa đất!"));

        // Cho phép Admin sửa sai thông tin
        if (dataMoi.getDienTichGoc() != null) dat.setDienTichGoc(dataMoi.getDienTichGoc());
        if (dataMoi.getMaLoaiDat() != null) dat.setMaLoaiDat(dataMoi.getMaLoaiDat());
        if (dataMoi.getDiaChi() != null) dat.setDiaChi(dataMoi.getDiaChi());
        if (dataMoi.getKhuVuc() != null) dat.setKhuVuc(dataMoi.getKhuVuc());
        // Có thể bổ sung update soTo, soThua nếu cần
        if (dataMoi.getSoTo() != null) dat.setSoTo(dataMoi.getSoTo());
        if (dataMoi.getSoThua() != null) dat.setSoThua(dataMoi.getSoThua());

        return thuaDatRepo.save(dat);
    }
}