package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.*;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.*;
import com.thanglong.quanlythuedat.usecase.IQuanLyHoSoUseCase;
import com.thanglong.quanlythuedat.usecase.dto.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    @Autowired private JpaNhatKyXuLyRepo nhatKyRepo; 
    @Autowired private JpaKhuVucRepo khuVucRepo;     

    @Override
    public HoSoOutputDTO nopHoSoKhaiThue(HoSoInputDTO input, MultipartFile file) {
        ThuaDatEntity thuaDat = thuaDatRepo.findById(input.getMaThuaDat())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thửa đất!"));

        boolean gianLan = false;
        StringBuilder msg = new StringBuilder();
        if (input.getDienTichKhaiBao() < thuaDat.getDienTichGoc() * 0.98) {
            gianLan = true;
            msg.append("Diện tích khai báo thấp hơn thực tế > 2%. ");
        }
        if (!input.getMucDichSuDungKhaiBao().equalsIgnoreCase(thuaDat.getMaLoaiDat())) {
            gianLan = true;
            msg.append("Sai mục đích sử dụng quy hoạch. ");
        }

        BangGiaDatEntity bangGia = bangGiaDatRepo.findByNamApDungAndMaKhuVucAndMaLoaiDat(
                input.getNamKhaiThue(), thuaDat.getMaKhuVuc(), thuaDat.getMaLoaiDat()
        ).orElseThrow(() -> new RuntimeException("Chưa có bảng giá phù hợp!"));

        KhuVucEntity khuVuc = khuVucRepo.findById(thuaDat.getMaKhuVuc())
                .orElseThrow(() -> new RuntimeException("Khu vực không tồn tại!"));
        
        LoaiDatEntity loaiDat = loaiDatRepo.findById(thuaDat.getMaLoaiDat()).orElseThrow();

        double tongGiaTri = input.getDienTichKhaiBao() * bangGia.getDonGiaM2() * khuVuc.getHeSoK();
        double thuePhaiNop = tongGiaTri * loaiDat.getThueSuat();

        HoSoEntity hoSo = new HoSoEntity();
        hoSo.setMaNguoiKhai(input.getMaNguoiDung());
        hoSo.setMaThuaDat(input.getMaThuaDat());
        hoSo.setNamKhaiThue(input.getNamKhaiThue());
        hoSo.setDienTichKhaiBao(input.getDienTichKhaiBao());
        hoSo.setMucDichSuDungKhaiBao(input.getMucDichSuDungKhaiBao());
        hoSo.setTongGiaTriDat(tongGiaTri);
        hoSo.setSoTienPhaiNop(thuePhaiNop);
        hoSo.setDauHieuGianLan(gianLan);
        
        String trangThaiBanDau = gianLan ? "CANH_BAO" : "CHO_DUYET";
        hoSo.setTrangThai(trangThaiBanDau);

        if (file != null && !file.isEmpty()) {
            hoSo.setFileDinhKemGiaoDich("HS_" + System.currentTimeMillis() + "_" + file.getOriginalFilename());
        }
        
        hoSo = hoSoRepo.save(hoSo);
        ghiNhatKy(hoSo.getMaHoSo(), null, "KHOI_TAO", trangThaiBanDau, "Nộp hồ sơ. " + msg);

        HoSoOutputDTO out = new HoSoOutputDTO();
        out.setMaHoSo(hoSo.getMaHoSo());
        out.setSoTienPhaiNop(thuePhaiNop);
        out.setTongGiaTriDat(tongGiaTri);
        out.setTrangThai(trangThaiBanDau);
        out.setDauHieuGianLan(gianLan);
        out.setThongBao(msg.toString());
        return out;
    }

    @Override
    public String duyetHoSo(Long maHoSo, boolean dongY, String lyDo) {
        HoSoEntity h = layChiTietHoSo(maHoSo);
        String ttCu = h.getTrangThai();
        String ttMoi = dongY ? "DA_DUYET" : "TU_CHOI";
        h.setTrangThai(ttMoi);
        h.setNgayDuyet(LocalDateTime.now());
        hoSoRepo.save(h);
        ghiNhatKy(maHoSo, 999L, ttCu, ttMoi, lyDo);
        return "Đã cập nhật trạng thái: " + ttMoi;
    }

    @Override
    public void thanhToanThue(Long maHoSo) {
        HoSoEntity h = layChiTietHoSo(maHoSo);
        if (!"DA_DUYET".equals(h.getTrangThai())) throw new RuntimeException("Hồ sơ chưa được duyệt!");
        String ttCu = h.getTrangThai();
        h.setTrangThai("DA_NOP_TIEN");
        hoSoRepo.save(h);
        ghiNhatKy(maHoSo, h.getMaNguoiKhai(), ttCu, "DA_NOP_TIEN", "Thanh toán thành công");
    }

    private void ghiNhatKy(Long maHoSo, Long maCanBo, String tu, String den, String ghiChu) {
        NhatKyXuLyEntity nk = new NhatKyXuLyEntity();
        nk.setMaHoSo(maHoSo);
        nk.setMaCanBo(maCanBo);
        nk.setTrangThaiTu(tu);
        nk.setTrangThaiDen(den);
        nk.setGhiChu(ghiChu);
        nk.setThoiGianXuLy(LocalDateTime.now());
        nhatKyRepo.save(nk);
    }

    // [FIX LỖI] Hàm này sẽ hết báo đỏ vì Interface đã có khai báo
    @Override
    public List<NhatKyXuLyEntity> xemLichSuXuLy(Long maHoSo) {
        return nhatKyRepo.findByMaHoSo(maHoSo);
    }

    @Override
    public BaoCaoThongKeDTO layBaoCaoThongKe(Integer nam, String khuVuc) {
        List<HoSoEntity> list = hoSoRepo.findAll();
        if (nam != null) {
            list = list.stream().filter(h -> h.getNamKhaiThue().equals(nam)).collect(Collectors.toList());
        }
        BaoCaoThongKeDTO dto = new BaoCaoThongKeDTO();
        dto.setTongSoHoSo(list.size());
        dto.setTongThuThue(list.stream().filter(h -> "DA_NOP_TIEN".equals(h.getTrangThai())).mapToDouble(h -> h.getSoTienPhaiNop() != null ? h.getSoTienPhaiNop() : 0).sum());
        dto.setTongNoThue(list.stream().filter(h -> "DA_DUYET".equals(h.getTrangThai())).mapToDouble(h -> h.getSoTienPhaiNop() != null ? h.getSoTienPhaiNop() : 0).sum());
        dto.setSoHoSoChoDuyet(list.stream().filter(h -> "CHO_DUYET".equals(h.getTrangThai())).count());
        dto.setSoHoSoDaDuyet(list.stream().filter(h -> "DA_DUYET".equals(h.getTrangThai())).count());
        dto.setSoHoSoGianLan(list.stream().filter(h -> Boolean.TRUE.equals(h.getDauHieuGianLan())).count());
        return dto;
    }

    // [FIX LỖI] Hàm xuất Excel cho Controller gọi
    @Override
    public ByteArrayInputStream xuatBaoCaoExcel() {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Bao Cao Thue");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Mã Hồ Sơ");
            header.createCell(1).setCellValue("Diện Tích");
            header.createCell(2).setCellValue("Tiền Thuế");
            header.createCell(3).setCellValue("Trạng Thái");

            List<HoSoEntity> list = hoSoRepo.findAll();
            int idx = 1;
            for (HoSoEntity h : list) {
                Row row = sheet.createRow(idx++);
                row.createCell(0).setCellValue(h.getMaHoSo());
                row.createCell(1).setCellValue(h.getDienTichKhaiBao());
                row.createCell(2).setCellValue(h.getSoTienPhaiNop() != null ? h.getSoTienPhaiNop() : 0);
                row.createCell(3).setCellValue(h.getTrangThai());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) { return null; }
    }

    @Override public HoSoEntity layChiTietHoSo(Long id) { return hoSoRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found")); }
    @Override public List<HoSoEntity> layDanhSachHoSo() { return hoSoRepo.findAll(); }
    @Override public List<HoSoEntity> layLichSuNopThue(Long id) { return hoSoRepo.findAll(); } 
    @Override public KhieuNaiEntity guiKhieuNai(KhieuNaiEntity k, MultipartFile f) { return khieuNaiRepo.save(k); }
}