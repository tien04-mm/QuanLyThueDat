package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.*;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.*;
import com.thanglong.quanlythuedat.usecase.IQuanLyHoSoUseCase;
import com.thanglong.quanlythuedat.usecase.dto.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

        // 1. Thuật toán chống gian lận (So sánh sai lệch 2%)
        if (input.getDienTichKhaiBao() < thuaDatGoc.getDienTichGoc() * 0.98) {
            trangThai = "CANH_BAO_GIAN_LAN";
            canhBao.append("Diện tích khai báo thấp hơn thực tế > 2%. ");
        }

        if (!input.getMucDichSuDung().equalsIgnoreCase(thuaDatGoc.getMaLoaiDat())) {
            trangThai = "CANH_BAO_GIAN_LAN";
            canhBao.append("Sai mục đích sử dụng quy hoạch. ");
        }

        // 2. Thuật toán tính thuế
        LoaiDatEntity loaiDat = loaiDatRepo.findById(thuaDatGoc.getMaLoaiDat())
                .orElseThrow(() -> new RuntimeException("Lỗi: Loại đất không tồn tại!"));
        
        BangGiaDatEntity bangGia = bangGiaDatRepo.findByNamApDungAndKhuVucAndMaLoaiDat(
                input.getNamKhaiThue(), thuaDatGoc.getKhuVuc(), thuaDatGoc.getMaLoaiDat()
        ).orElseThrow(() -> new RuntimeException("Chưa có bảng giá năm " + input.getNamKhaiThue()));

        double soTienThue = input.getDienTichKhaiBao() * bangGia.getDonGiaM2() * loaiDat.getThueSuat();

        // Lưu DB
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
        output.setMaHoSo(hoSo.getMaHoSo());
        output.setSoTienThue(soTienThue);
        output.setTrangThai(trangThai);
        output.setThongBao(canhBao.length() > 0 ? canhBao.toString() : "Nộp thành công! Chờ duyệt.");
        return output;
    }

    @Override
    public List<HoSoEntity> layDanhSachHoSo() { return hoSoRepo.findAll(); }

    @Override
    public HoSoEntity layChiTietHoSo(Long id) {
        return hoSoRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));
    }

    @Override
    public String duyetHoSo(Long maHoSo, boolean dongY, String lyDo) {
        HoSoEntity h = hoSoRepo.findById(maHoSo).orElseThrow();
        h.setTrangThai(dongY ? "DA_DUYET" : "TU_CHOI"); // Duyệt xong -> Chờ thanh toán
        h.setGhiChu(lyDo);
        hoSoRepo.save(h);
        return "Đã cập nhật trạng thái hồ sơ.";
    }

    // [MỚI] Thanh toán
    @Override
    public void thanhToanThue(Long maHoSo) {
        HoSoEntity h = hoSoRepo.findById(maHoSo).orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ"));
        if (!"DA_DUYET".equals(h.getTrangThai())) {
            throw new RuntimeException("Hồ sơ chưa được duyệt hoặc đã thanh toán!");
        }
        h.setTrangThai("HOAN_THANH"); // Trạng thái cuối cùng
        h.setGhiChu(h.getGhiChu() + " | Đã thanh toán lúc " + LocalDateTime.now());
        hoSoRepo.save(h);
    }

    // [MỚI] Xuất Excel (Dùng Apache POI)
    @Override
    public ByteArrayInputStream xuatBaoCaoExcel() {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Danh sách hồ sơ");

            // Header
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Người Khai", "Diện Tích", "Thuế (VNĐ)", "Trạng Thái"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Data
            List<HoSoEntity> hosos = hoSoRepo.findAll();
            int rowIdx = 1;
            for (HoSoEntity h : hosos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(h.getMaHoSo());
                row.createCell(1).setCellValue(h.getMaNguoiKhai());
                row.createCell(2).setCellValue(h.getDienTichKhaiBao());
                row.createCell(3).setCellValue(h.getSoTienThue());
                row.createCell(4).setCellValue(h.getTrangThai());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Lỗi xuất Excel: " + e.getMessage());
        }
    }

    @Override
    public List<HoSoEntity> layLichSuNopThue(Long maNguoiDung) {
        return hoSoRepo.findAll().stream()
                .filter(h -> h.getMaNguoiKhai().equals(maNguoiDung))
                .collect(Collectors.toList());
    }

    @Override
    public BaoCaoThongKeDTO layBaoCaoThongKe() {
        List<HoSoEntity> list = hoSoRepo.findAll();
        BaoCaoThongKeDTO dto = new BaoCaoThongKeDTO();
        dto.setTongSoHoSo(list.size());
        dto.setTongTienThueDuKien(list.stream().mapToDouble(HoSoEntity::getSoTienThue).sum());
        dto.setSoHoSoChoDuyet(list.stream().filter(h -> "CHO_DUYET".equals(h.getTrangThai())).count());
        dto.setSoHoSoDaDuyet(list.stream().filter(h -> "DA_DUYET".equals(h.getTrangThai())).count());
        dto.setSoHoSoGianLan(list.stream().filter(h -> "CANH_BAO_GIAN_LAN".equals(h.getTrangThai())).count());
        return dto;
    }

    @Override
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