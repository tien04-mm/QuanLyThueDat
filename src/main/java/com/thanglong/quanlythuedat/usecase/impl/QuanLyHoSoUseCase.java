package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.HoSoEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.ThuaDatEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.JpaBangGiaDatRepo;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.JpaHoSoRepo;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.JpaThuaDatRepo;
import com.thanglong.quanlythuedat.usecase.IQuanLyHoSoUseCase;
import com.thanglong.quanlythuedat.usecase.dto.BaoCaoThongKeDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoInputDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuanLyHoSoUseCase implements IQuanLyHoSoUseCase {

    @Autowired
    private JpaThuaDatRepo thuaDatRepo;

    @Autowired
    private JpaHoSoRepo hoSoRepo;
    
    @Autowired
    private JpaBangGiaDatRepo bangGiaDatRepo;

    // --- CHỨC NĂNG 1: NỘP HỒ SƠ ---
    @Override
    public HoSoOutputDTO nopHoSoKhaiThue(HoSoInputDTO input) {
        ThuaDatEntity thuaDatEntity = thuaDatRepo.findById(input.getMaThuaDat())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thửa đất!"));

        double dienTichGoc = thuaDatEntity.getDienTichGoc();
        double dienTichKhai = input.getDienTichKhaiBao();

        String trangThaiXuLy = "CHO_DUYET";
        String ghiChu = "";

        if (dienTichKhai < dienTichGoc * 0.98) {
            trangThaiXuLy = "CANH_BAO_GIAN_LAN";
            ghiChu = "Hệ thống phát hiện diện tích khai báo (" + dienTichKhai +
                    ") nhỏ hơn diện tích gốc (" + dienTichGoc + "). Vui lòng giải trình.";
        }

        String loaiDat = thuaDatEntity.getLoaiDatQuyHoach();
        BangGiaDatEntity bangGia = bangGiaDatRepo.findByLoaiDat(loaiDat)
                .orElseThrow(() -> new RuntimeException("Lỗi: Chưa có bảng giá quy định cho loại đất: " + loaiDat));

        double soTienThue = dienTichKhai * bangGia.getDonGiaM2() * bangGia.getThueSuat();

        HoSoEntity hoSoMoi = new HoSoEntity();
        hoSoMoi.setMaNguoiKhai(input.getMaNguoiDung());
        hoSoMoi.setMaThuaDat(input.getMaThuaDat());
        hoSoMoi.setNamKhaiThue(input.getNamKhaiThue());
        hoSoMoi.setDienTichKhaiBao(dienTichKhai);
        hoSoMoi.setMucDichSuDung(input.getMucDichSuDung());
        hoSoMoi.setSoTienThue(soTienThue);
        hoSoMoi.setNgayNop(LocalDateTime.now());
        hoSoMoi.setTrangThai(trangThaiXuLy);
        hoSoMoi.setGhiChu(ghiChu);

        hoSoRepo.save(hoSoMoi);

        HoSoOutputDTO output = new HoSoOutputDTO();
        output.setMaHoSo(hoSoMoi.getId());
        output.setSoTienThue(soTienThue);
        output.setTrangThai(trangThaiXuLy);
        output.setThongBao(ghiChu.isEmpty() ? "Nộp hồ sơ thành công!" : "Hồ sơ bị cảnh báo gian lận!");

        return output;
    }

    // --- CHỨC NĂNG 2: XEM DANH SÁCH ---
    @Override
    public List<HoSoEntity> layDanhSachHoSo() {
        return hoSoRepo.findAll();
    }

    // --- CHỨC NĂNG 3: DUYỆT HỒ SƠ ---
    @Override
    public String duyetHoSo(Long maHoSo, boolean dongY, String lyDo) {
        HoSoEntity hoSo = hoSoRepo.findById(maHoSo)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ!"));

        if (dongY) {
            hoSo.setTrangThai("DA_DUYET");
            hoSo.setGhiChu(lyDo);
        } else {
            hoSo.setTrangThai("TU_CHOI");
            hoSo.setGhiChu(lyDo);
        }

        hoSoRepo.save(hoSo);
        return "Đã cập nhật trạng thái hồ sơ thành: " + hoSo.getTrangThai();
    }

    // --- CHỨC NĂNG 4: TRA CỨU ĐẤT CỦA TÔI ---
    @Override
    public List<ThuaDatEntity> traCuuDatCuaToi(Long maChuSoHuu) {
        return thuaDatRepo.findByMaChuSoHuu(maChuSoHuu);
    }

    @Override
    public List<HoSoEntity> layLichSuNopThue(Long maNguoiDung) {
        List<HoSoEntity> all = hoSoRepo.findAll();
        return all.stream()
                .filter(h -> h.getMaNguoiKhai().equals(maNguoiDung))
                .collect(Collectors.toList());
    }

    // --- [MỚI] CHỨC NĂNG 5: BÁO CÁO THỐNG KÊ ---
    @Override
    public BaoCaoThongKeDTO layBaoCaoThongKe() {
        List<HoSoEntity> tatCaHoSo = hoSoRepo.findAll();

        BaoCaoThongKeDTO baoCao = new BaoCaoThongKeDTO();
        
        // 1. Tổng số lượng
        baoCao.setTongSoHoSo(tatCaHoSo.size());

        // 2. Tổng tiền thuế (Cộng dồn cột soTienThue)
        double tongTien = tatCaHoSo.stream()
                .mapToDouble(HoSoEntity::getSoTienThue)
                .sum();
        baoCao.setTongTienThueDuKien(tongTien);

        // 3. Đếm theo trạng thái (Dùng Stream filter cho gọn)
        long choDuyet = tatCaHoSo.stream().filter(h -> "CHO_DUYET".equals(h.getTrangThai())).count();
        long daDuyet = tatCaHoSo.stream().filter(h -> "DA_DUYET".equals(h.getTrangThai())).count();
        long tuChoi = tatCaHoSo.stream().filter(h -> "TU_CHOI".equals(h.getTrangThai())).count();
        long gianLan = tatCaHoSo.stream().filter(h -> "CANH_BAO_GIAN_LAN".equals(h.getTrangThai())).count();

        baoCao.setSoHoSoChoDuyet(choDuyet);
        baoCao.setSoHoSoDaDuyet(daDuyet);
        baoCao.setSoHoSoBiTuChoi(tuChoi);
        baoCao.setSoHoSoGianLan(gianLan);

        return baoCao;
    }
}