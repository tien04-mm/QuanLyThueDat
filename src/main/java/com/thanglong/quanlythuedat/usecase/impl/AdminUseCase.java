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

@Service
public class AdminUseCase implements IAdminUseCase {

    @Autowired private JpaBangGiaDatRepo bangGiaDatRepo;
    @Autowired private JpaNguoiDungRepo nguoiDungRepo;
    @Autowired private JpaThuaDatRepo thuaDatRepo;

    @Override
    public BangGiaDatEntity capNhatBangGiaDat(BangGiaDatEntity bangGiaMoi) {
        Optional<BangGiaDatEntity> existing = bangGiaDatRepo.findByNamApDungAndKhuVucAndMaLoaiDat(
                bangGiaMoi.getNamApDung(), bangGiaMoi.getKhuVuc(), bangGiaMoi.getMaLoaiDat());
        
        if (existing.isPresent()) {
            BangGiaDatEntity bangGiaCu = existing.get();
            bangGiaCu.setDonGiaM2(bangGiaMoi.getDonGiaM2());
            return bangGiaDatRepo.save(bangGiaCu);
        } else {
            return bangGiaDatRepo.save(bangGiaMoi);
        }
    }

    @Override
    public NguoiDungEntity taoTaiKhoanCanBo(NguoiDungEntity canBoMoi) {
        if (nguoiDungRepo.existsByTenDangNhap(canBoMoi.getTenDangNhap())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        if (nguoiDungRepo.existsByCccd(canBoMoi.getCccd())) {
            throw new RuntimeException("CCCD đã tồn tại!");
        }
        canBoMoi.setVaiTro("CAN_BO");
        canBoMoi.setHoatDong(true);
        if (canBoMoi.getMatKhau() == null || canBoMoi.getMatKhau().isEmpty()) {
            canBoMoi.setMatKhau("123456"); 
        }
        return nguoiDungRepo.save(canBoMoi);
    }

    @Override
    public String importDuLieuDatDai(MultipartFile file) throws IOException {
        // (Giữ nguyên logic Import Excel cũ ở đây - code dài nên tôi không paste lại đoạn import để tiết kiệm không gian, bạn giữ nguyên nhé)
        List<ThuaDatEntity> danhSachDat = new ArrayList<>();
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 
                ThuaDatEntity dat = new ThuaDatEntity();
                dat.setSoTo(getCellValue(row, 0));
                dat.setSoThua(getCellValue(row, 1));
                dat.setDiaChi(getCellValue(row, 2));
                dat.setKhuVuc(getCellValue(row, 3));
                Cell cellDienTich = row.getCell(4);
                if (cellDienTich != null) dat.setDienTichGoc(cellDienTich.getNumericCellValue());
                dat.setMaLoaiDat(getCellValue(row, 5));
                Cell cellChu = row.getCell(6);
                if (cellChu != null) dat.setMaChuSoHuu((long) cellChu.getNumericCellValue());
                danhSachDat.add(dat);
            }
        }
        if (!danhSachDat.isEmpty()) {
            thuaDatRepo.saveAll(danhSachDat);
            return "Import thành công " + danhSachDat.size() + " thửa đất!";
        } else {
            return "File rỗng hoặc không đọc được dữ liệu!";
        }
    }
    
    private String getCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> "";
        };
    }

    @Override
    public void xoaNguoiDung(Long id) {
        if (!nguoiDungRepo.existsById(id)) throw new RuntimeException("Không tìm thấy user!");
        nguoiDungRepo.deleteById(id);
    }

    @Override
    public void xoaThuaDat(Long id) {
        if (!thuaDatRepo.existsById(id)) throw new RuntimeException("Không tìm thấy đất!");
        thuaDatRepo.deleteById(id);
    }

    // --- [MỚI] CHỨC NĂNG KHÓA TÀI KHOẢN (Khớp Activity) ---
    @Override
    public void khoaTaiKhoan(Long id) {
        NguoiDungEntity user = nguoiDungRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        
        user.setHoatDong(false); // Set thành FALSE
        nguoiDungRepo.save(user);
    }
}