package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.*;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.*;
import com.thanglong.quanlythuedat.usecase.IAdminUseCase;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminUseCase implements IAdminUseCase {

    @Autowired private JpaNguoiDungRepo nguoiDungRepo;
    @Autowired private JpaBangGiaDatRepo bangGiaDatRepo;
    @Autowired private JpaThuaDatRepo thuaDatRepo;

    // ==========================================================
    // PHẦN 1: QUẢN LÝ TÀI KHOẢN
    // ==========================================================

    @Override
    public NguoiDungEntity taoTaiKhoanNhanVien(NguoiDungEntity nvMoi) {
        if (nguoiDungRepo.existsByTenDangNhap(nvMoi.getTenDangNhap())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        
        // [FIX] Kiểm tra trùng Số định danh (method đã thêm ở bước trước)
        if (nvMoi.getSoDinhDanh() != null && nguoiDungRepo.existsBySoDinhDanh(nvMoi.getSoDinhDanh())) {
            throw new RuntimeException("Số định danh (CCCD) đã tồn tại!");
        }

        // Mặc định vai trò nếu chưa có (setVaiTro sẽ tự map sang số int: 2=CAN_BO)
        if (nvMoi.getMaVaiTro() == null) {
            nvMoi.setVaiTro("CAN_BO");
        }

        nvMoi.setTrangThai(true); 
        
        if (nvMoi.getMatKhau() == null) {
            nvMoi.setMatKhau("123456");
        }

        return nguoiDungRepo.save(nvMoi);
    }

    @Override
    public void pheDuyetTaiKhoan(Long maNguoiDung) {
        NguoiDungEntity user = nguoiDungRepo.findById(maNguoiDung)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        user.setTrangThai(true);
        nguoiDungRepo.save(user);
    }

    @Override
    public void khoaTaiKhoan(Long maNguoiDung) {
        NguoiDungEntity user = nguoiDungRepo.findById(maNguoiDung)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        user.setTrangThai(false);
        nguoiDungRepo.save(user);
    }

    @Override
    public void xoaNguoiDung(Long maNguoiDung) {
        if (!nguoiDungRepo.existsById(maNguoiDung)) {
            throw new RuntimeException("Không tìm thấy người dùng để xóa!");
        }
        nguoiDungRepo.deleteById(maNguoiDung);
    }

    @Override
    public NguoiDungEntity capNhatThongTin(Long maNguoiDung, NguoiDungEntity dataMoi) {
        NguoiDungEntity user = nguoiDungRepo.findById(maNguoiDung)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));

        if (dataMoi.getHoTen() != null) user.setHoTen(dataMoi.getHoTen());
        if (dataMoi.getSdt() != null) user.setSdt(dataMoi.getSdt());
        if (dataMoi.getEmail() != null) user.setEmail(dataMoi.getEmail());
        if (dataMoi.getDiaChi() != null) user.setDiaChi(dataMoi.getDiaChi());
        
        return nguoiDungRepo.save(user);
    }

    @Override
    public List<NguoiDungEntity> timKiemNguoiDung(String vaiTro, String keyword) {
        List<NguoiDungEntity> all = nguoiDungRepo.findAll();
        
        return all.stream()
                .filter(u -> vaiTro == null || u.getVaiTro().equalsIgnoreCase(vaiTro))
                .filter(u -> keyword == null || 
                        (u.getHoTen() != null && u.getHoTen().toLowerCase().contains(keyword.toLowerCase())) ||
                        (u.getSoDinhDanh() != null && u.getSoDinhDanh().contains(keyword)))
                .collect(Collectors.toList());
    }

    // ==========================================================
    // PHẦN 2: QUẢN LÝ DỮ LIỆU ĐẤT ĐAI
    // ==========================================================

    @Override
    public BangGiaDatEntity capNhatBangGiaDat(BangGiaDatEntity bangGiaMoi) {
        // [FIX] Sửa logic tìm kiếm: Dùng MaKhuVuc (Int), MaLoaiDat (Int) và TrangThai
        // Bỏ logic tìm theo namApDung vì DB mới không có
        Optional<BangGiaDatEntity> existing = bangGiaDatRepo.findByMaKhuVucAndMaLoaiDatAndTrangThai(
                bangGiaMoi.getMaKhuVuc(), 
                bangGiaMoi.getMaLoaiDat(),
                "Hiệu lực"
        );

        if (existing.isPresent()) {
            BangGiaDatEntity cu = existing.get();
            cu.setDonGiaM2(bangGiaMoi.getDonGiaM2());
            // Cập nhật ngày hiệu lực nếu cần
            if (bangGiaMoi.getNgayBanHanh() != null) cu.setNgayBanHanh(bangGiaMoi.getNgayBanHanh());
            return bangGiaDatRepo.save(cu);
        } else {
            if (bangGiaMoi.getTrangThai() == null) bangGiaMoi.setTrangThai("Hiệu lực");
            return bangGiaDatRepo.save(bangGiaMoi);
        }
    }

    @Override
    public void xoaThuaDat(Long maThuaDat) {
        if (!thuaDatRepo.existsById(maThuaDat)) {
            throw new RuntimeException("Không tìm thấy thửa đất!");
        }
        thuaDatRepo.deleteById(maThuaDat);
    }

    @Override
    public String importDuLieuDatDai(MultipartFile file) throws IOException {
        List<ThuaDatEntity> danhSach = new ArrayList<>();
        
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 

                ThuaDatEntity dat = new ThuaDatEntity();
                // Map cột Excel: 0=SoTo, 1=SoThua, 2=DiaChi, 3=MaKhuVuc(ID), 4=DienTich, 5=MaLoaiDat(ID)
                dat.setSoTo(getCellValue(row, 0));
                dat.setSoThua(getCellValue(row, 1));
                dat.setDiaChiChiTiet(getCellValue(row, 2)); 
                
                // [FIX] Ép kiểu String từ Excel sang Integer vì DB lưu ID số
                dat.setMaKhuVuc(parseInteger(getCellValue(row, 3)));
                
                Cell cellDT = row.getCell(4);
                if (cellDT != null) dat.setDienTichGoc(cellDT.getNumericCellValue());
                
                // [FIX] Ép kiểu String từ Excel sang Integer
                dat.setMaLoaiDat(parseInteger(getCellValue(row, 5)));
                
                dat.setNgayTao(java.time.LocalDateTime.now());
                dat.setTrangThai("Đang sử dụng");

                danhSach.add(dat);
            }
        }

        if (!danhSach.isEmpty()) {
            thuaDatRepo.saveAll(danhSach);
            return "Import thành công " + danhSach.size() + " dòng dữ liệu!";
        } else {
            return "File rỗng hoặc lỗi định dạng!";
        }
    }

    // Hàm đọc cell Excel sang String
    private String getCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> "";
        };
    }
    
    // [THÊM MỚI] Hàm an toàn để ép kiểu String sang Integer
    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null; // Hoặc ném lỗi nếu bắt buộc
        }
    }
}