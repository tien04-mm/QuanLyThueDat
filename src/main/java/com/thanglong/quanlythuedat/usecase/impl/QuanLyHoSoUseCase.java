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

    // ==========================================================
    // CHỨC NĂNG 1: NỘP HỒ SƠ & TÍNH TOÁN (Quan trọng nhất)
    // ==========================================================
    @Override
    public HoSoOutputDTO nopHoSoKhaiThue(HoSoInputDTO input, MultipartFile file) {
        ThuaDatEntity thuaDatGoc = thuaDatRepo.findById(input.getMaThuaDat())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thửa đất gốc!"));

        String trangThai = "CHO_DUYET";
        StringBuilder canhBao = new StringBuilder();

        // --- [KIỂM TRA 1] THUẬT TOÁN CHỐNG GIAN LẬN (Vẫn còn nguyên) ---
        // So sánh diện tích khai báo với diện tích gốc (Lệch 2%)
        if (input.getDienTichKhaiBao() < thuaDatGoc.getDienTichGoc() * 0.98) {
            trangThai = "CANH_BAO_GIAN_LAN";
            canhBao.append("Diện tích khai báo thấp hơn thực tế > 2%. ");
        }
        
        // --- [KIỂM TRA 2] SO SÁNH QUY HOẠCH (Vẫn còn nguyên) ---
        if (!input.getMucDichSuDung().equalsIgnoreCase(thuaDatGoc.getMaLoaiDat())) {
             trangThai = "CANH_BAO_GIAN_LAN";
             canhBao.append("Sai mục đích sử dụng quy hoạch. ");
        }

        // --- [TÍNH TOÁN] CÔNG THỨC TÍNH THUẾ (Vẫn còn nguyên) ---
        LoaiDatEntity loaiDat = loaiDatRepo.findById(thuaDatGoc.getMaLoaiDat()).orElseThrow();
        BangGiaDatEntity bangGia = bangGiaDatRepo.findByNamApDungAndKhuVucAndMaLoaiDat(
                input.getNamKhaiThue(), thuaDatGoc.getKhuVuc(), thuaDatGoc.getMaLoaiDat()
        ).orElseThrow(() -> new RuntimeException("Chưa có bảng giá năm " + input.getNamKhaiThue()));

        double soTienThue = input.getDienTichKhaiBao() * bangGia.getDonGiaM2() * loaiDat.getThueSuat();

        // Lưu vào DB
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
        
        // [MỚI] Code thêm vào để Lưu File
        if (file != null && !file.isEmpty()) {
            hoSo.setFileDinhKem("hoso_" + System.currentTimeMillis() + "_" + file.getOriginalFilename());
        }
        // [MỚI] Code thêm vào để Ghi Log Lịch Sử
        hoSo.setLichSuXuLy(LocalDateTime.now() + ": Người dân nộp tờ khai.");

        hoSoRepo.save(hoSo);

        HoSoOutputDTO output = new HoSoOutputDTO();
        output.setMaHoSo(hoSo.getMaHoSo());
        output.setSoTienThue(soTienThue);
        output.setTrangThai(trangThai);
        output.setThongBao(canhBao.length() > 0 ? canhBao.toString() : "Nộp thành công!");
        return output;
    }

    // ==========================================================
    // CHỨC NĂNG 2: BÁO CÁO THỐNG KÊ (Đã được nâng cấp)
    // ==========================================================
    @Override
    public BaoCaoThongKeDTO layBaoCaoThongKe(Integer nam, String khuVuc) {
        List<HoSoEntity> list = hoSoRepo.findAll();
        
        // Logic lọc mới thêm vào
        if (nam != null) {
            list = list.stream().filter(h -> h.getNamKhaiThue().equals(nam)).collect(Collectors.toList());
        }
        
        BaoCaoThongKeDTO dto = new BaoCaoThongKeDTO();
        dto.setTongSoHoSo(list.size());
        // Tính toán chi tiết
        dto.setTongThuThue(list.stream().filter(h -> "HOAN_THANH".equals(h.getTrangThai())).mapToDouble(HoSoEntity::getSoTienThue).sum());
        dto.setTongNoThue(list.stream().filter(h -> "DA_DUYET".equals(h.getTrangThai())).mapToDouble(HoSoEntity::getSoTienThue).sum());
        dto.setSoHoSoChoDuyet(list.stream().filter(h -> "CHO_DUYET".equals(h.getTrangThai())).count());
        dto.setSoHoSoDaDuyet(list.stream().filter(h -> "DA_DUYET".equals(h.getTrangThai())).count());
        dto.setSoHoSoGianLan(list.stream().filter(h -> "CANH_BAO_GIAN_LAN".equals(h.getTrangThai())).count());
        
        return dto;
    }

    // ==========================================================
    // CHỨC NĂNG 3: DUYỆT & THANH TOÁN (Vẫn giữ nguyên logic)
    // ==========================================================
    @Override
    public String duyetHoSo(Long maHoSo, boolean dongY, String lyDo) {
        HoSoEntity h = layChiTietHoSo(maHoSo);
        String ttCu = h.getTrangThai();
        String ttMoi = dongY ? "DA_DUYET" : "TU_CHOI";
        
        h.setTrangThai(ttMoi);
        h.setGhiChu((h.getGhiChu() == null ? "" : h.getGhiChu()) + " | Lý do: " + lyDo);
        
        // [MỚI] Ghi log lịch sử
        h.setLichSuXuLy((h.getLichSuXuLy() == null ? "" : h.getLichSuXuLy()) + "\n" + LocalDateTime.now() + ": Cán bộ chuyển trạng thái " + ttCu + " -> " + ttMoi);
        
        hoSoRepo.save(h);
        return "Đã cập nhật trạng thái hồ sơ!";
    }

    @Override
    public void thanhToanThue(Long maHoSo) {
        HoSoEntity h = layChiTietHoSo(maHoSo);
        if (!"DA_DUYET".equals(h.getTrangThai())) throw new RuntimeException("Hồ sơ chưa được duyệt hoặc đã thanh toán!");
        h.setTrangThai("HOAN_THANH");
        h.setLichSuXuLy(h.getLichSuXuLy() + "\n" + LocalDateTime.now() + ": Đã thanh toán thuế.");
        hoSoRepo.save(h);
    }

    // ==========================================================
    // CHỨC NĂNG 4: CÁC TIỆN ÍCH KHÁC (Khiếu nại, Excel, Lịch sử)
    // ==========================================================
    @Override
    public KhieuNaiEntity guiKhieuNai(KhieuNaiEntity data, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            data.setFileMinhChung("kn_" + System.currentTimeMillis() + "_" + file.getOriginalFilename());
        }
        data.setNgayGui(LocalDateTime.now());
        data.setTrangThai("CHO_XU_LY");
        return khieuNaiRepo.save(data);
    }

    @Override
    public ByteArrayInputStream xuatBaoCaoExcel() {
        // Vẫn giữ logic tạo file Excel bằng Apache POI
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Danh sách hồ sơ");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Mã HS");
            header.createCell(1).setCellValue("Tiền Thuế");
            header.createCell(2).setCellValue("Trạng Thái");
            
            List<HoSoEntity> list = hoSoRepo.findAll();
            int rowIdx = 1;
            for (HoSoEntity h : list) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(h.getMaHoSo());
                row.createCell(1).setCellValue(h.getSoTienThue());
                row.createCell(2).setCellValue(h.getTrangThai());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) { return null; }
    }

    // Các hàm getter đơn giản
    @Override public List<HoSoEntity> layDanhSachHoSo() { return hoSoRepo.findAll(); }
    @Override public HoSoEntity layChiTietHoSo(Long id) { return hoSoRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ")); }
    @Override public List<HoSoEntity> layLichSuNopThue(Long maNguoiDung) { return hoSoRepo.findAll().stream().filter(h -> h.getMaNguoiKhai().equals(maNguoiDung)).collect(Collectors.toList()); }
    @Override public String layLichSuXuLy(Long maHoSo) { return layChiTietHoSo(maHoSo).getLichSuXuLy(); }
}