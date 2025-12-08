package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.*;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.*;
import com.thanglong.quanlythuedat.usecase.IQuanLyHoSoUseCase;
import com.thanglong.quanlythuedat.usecase.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuanLyHoSoUseCase implements IQuanLyHoSoUseCase {

    @Autowired private JpaThuaDatRepo thuaDatRepo;
    @Autowired private JpaHoSoRepo hoSoRepo;
    @Autowired private JpaBangGiaDatRepo bangGiaDatRepo;
    @Autowired private JpaLoaiDatRepo loaiDatRepo;
    @Autowired private JpaKhieuNaiRepo khieuNaiRepo;

    @Override
    public HoSoOutputDTO nopHoSoKhaiThue(HoSoInputDTO input) {
        ThuaDatEntity thuaDatGoc = thuaDatRepo.findById(input.getMaThuaDat())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thửa đất gốc!"));

        String trangThai = "CHO_DUYET";
        StringBuilder canhBao = new StringBuilder();

        if (input.getDienTichKhaiBao() < thuaDatGoc.getDienTichGoc() * 0.98) {
            trangThai = "CANH_BAO_GIAN_LAN";
            canhBao.append("Diện tích khai báo thấp hơn thực tế. ");
        }

        if (!input.getMucDichSuDung().equalsIgnoreCase(thuaDatGoc.getMaLoaiDat())) {
            trangThai = "CANH_BAO_GIAN_LAN";
            canhBao.append("Mục đích sử dụng sai với quy hoạch (" + thuaDatGoc.getMaLoaiDat() + "). ");
        }

        LoaiDatEntity loaiDat = loaiDatRepo.findById(thuaDatGoc.getMaLoaiDat())
                .orElseThrow(() -> new RuntimeException("Lỗi: Loại đất không tồn tại trong danh mục!"));
        
        BangGiaDatEntity bangGia = bangGiaDatRepo.findByNamApDungAndKhuVucAndMaLoaiDat(
                input.getNamKhaiThue(), 
                thuaDatGoc.getKhuVuc(), 
                thuaDatGoc.getMaLoaiDat()
        ).orElseThrow(() -> new RuntimeException("Chưa có bảng giá cho năm " + input.getNamKhaiThue() + " tại " + thuaDatGoc.getKhuVuc()));

        double soTienThue = input.getDienTichKhaiBao() * bangGia.getDonGiaM2() * loaiDat.getThueSuat();

        HoSoEntity hoSo = new HoSoEntity();
        hoSo.setMaNguoiKhai(input.getMaNguoiDung());
        hoSo.setMaThuaDat(input.getMaThuaDat());
        hoSo.setNamKhaiThue(input.getNamKhaiThue());
        hoSo.setDienTichKhaiBao(input.getDienTichKhaiBao());
        hoSo.setMucDichSuDung(input.getMucDichSuDung());
        hoSo.setSoTienThue(soTienThue);
        hoSo.setNgayNop(LocalDateTime.now());
        hoSo.setTrangThai(trangThai);
        hoSo.setGhiChu(canhBao.toString());

        hoSoRepo.save(hoSo);

        HoSoOutputDTO output = new HoSoOutputDTO();
        
        // [CẬP NHẬT] Sửa getId() thành getMaHoSo()
        output.setMaHoSo(hoSo.getMaHoSo()); 
        
        output.setSoTienThue(soTienThue);
        output.setTrangThai(trangThai);
        output.setThongBao(canhBao.length() > 0 ? canhBao.toString() : "Nộp hồ sơ thành công!");
        
        return output;
    }

    @Override
    public List<HoSoEntity> layDanhSachHoSo() { return hoSoRepo.findAll(); }
    
    @Override
    public String duyetHoSo(Long maHoSo, boolean dongY, String lyDo) {
        HoSoEntity h = hoSoRepo.findById(maHoSo).orElseThrow();
        h.setTrangThai(dongY ? "DA_DUYET" : "TU_CHOI");
        h.setGhiChu(lyDo);
        hoSoRepo.save(h);
        return "Xong";
    }
    
    @Override
    public List<ThuaDatEntity> traCuuDatCuaToi(Long maChuSoHuu) {
        return thuaDatRepo.findByMaChuSoHuu(maChuSoHuu);
    }
    
    @Override
    public List<HoSoEntity> layLichSuNopThue(Long maNguoiDung) {
         return hoSoRepo.findAll().stream()
                .filter(h -> h.getMaNguoiKhai().equals(maNguoiDung))
                .collect(Collectors.toList());
    }

    @Override
    public BaoCaoThongKeDTO layBaoCaoThongKe() {
        List<HoSoEntity> tatCaHoSo = hoSoRepo.findAll();
        BaoCaoThongKeDTO baoCao = new BaoCaoThongKeDTO();
        baoCao.setTongSoHoSo(tatCaHoSo.size());
        double tongTien = tatCaHoSo.stream().mapToDouble(HoSoEntity::getSoTienThue).sum();
        baoCao.setTongTienThueDuKien(tongTien);
        
        baoCao.setSoHoSoChoDuyet(tatCaHoSo.stream().filter(h -> "CHO_DUYET".equals(h.getTrangThai())).count());
        baoCao.setSoHoSoDaDuyet(tatCaHoSo.stream().filter(h -> "DA_DUYET".equals(h.getTrangThai())).count());
        baoCao.setSoHoSoBiTuChoi(tatCaHoSo.stream().filter(h -> "TU_CHOI".equals(h.getTrangThai())).count());
        baoCao.setSoHoSoGianLan(tatCaHoSo.stream().filter(h -> "CANH_BAO_GIAN_LAN".equals(h.getTrangThai())).count());

        return baoCao;
    }
    
    // Giữ nguyên phần Khiếu nại (vì không đụng đến id hồ sơ ở đây)
    public KhieuNaiEntity guiKhieuNai(Long maHoSo, Long maNguoiGui, String noiDung) {
        KhieuNaiEntity kn = new KhieuNaiEntity();
        kn.setMaHoSo(maHoSo);
        kn.setMaNguoiGui(maNguoiGui);
        kn.setNoiDung(noiDung);
        kn.setNgayGui(LocalDateTime.now());
        kn.setTrangThai("CHO_XU_LY");
        return khieuNaiRepo.save(kn);
    }
}