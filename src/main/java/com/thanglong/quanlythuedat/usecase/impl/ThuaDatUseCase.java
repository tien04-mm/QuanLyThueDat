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
        return thuaDatRepo.findByMaChuSoHuu(maChuSoHuu);
    }

    @Override
    public ThuaDatEntity timThuaDat(String soTo, String soThua) {
        return thuaDatRepo.findBySoToAndSoThua(soTo, soThua)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dữ liệu đất với Số tờ/Số thửa này!"));
    }

    @Override
    public ThuaDatEntity capNhatThongTinDat(Long id, ThuaDatEntity dataMoi) {
        ThuaDatEntity dat = thuaDatRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thửa đất!"));

        if (dataMoi.getDienTichGoc() != null) dat.setDienTichGoc(dataMoi.getDienTichGoc());
        if (dataMoi.getMaLoaiDat() != null) dat.setMaLoaiDat(dataMoi.getMaLoaiDat());
        if (dataMoi.getDiaChiChiTiet() != null) dat.setDiaChiChiTiet(dataMoi.getDiaChiChiTiet());
        if (dataMoi.getMaKhuVuc() != null) dat.setMaKhuVuc(dataMoi.getMaKhuVuc());
        if (dataMoi.getSoTo() != null) dat.setSoTo(dataMoi.getSoTo());
        if (dataMoi.getSoThua() != null) dat.setSoThua(dataMoi.getSoThua());

        return thuaDatRepo.save(dat);
    }
}