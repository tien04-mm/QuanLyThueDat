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

    // --- LOGIC QUẢN LÝ NGƯỜI DÙNG ---

    @Override
    public NguoiDungEntity taoTaiKhoanNhanVien(NguoiDungEntity nvMoi) {
        if (nguoiDungRepo.existsByTenDangNhap(nvMoi.getTenDangNhap())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        if (nvMoi.getCccd() != null && nguoiDungRepo.existsByCccd(nvMoi.getCccd())) {
            throw new RuntimeException("CCCD đã tồn tại!");
        }

        String roleYeuCau = nvMoi.getVaiTro();
        if ("QL_DAT_DAI".equals(roleYeuCau)) {
            nvMoi.setVaiTro("QL_DAT_DAI"); 
        } else if ("CAN_BO".equals(roleYeuCau)) {
            nvMoi.setVaiTro("CAN_BO");
        } else {
            nvMoi.setVaiTro("CAN_BO");
        }

        nvMoi.setHoatDong(true); // Nhân viên do Admin tạo thì Active luôn
        if (nvMoi.getMatKhau() == null || nvMoi.getMatKhau().isEmpty()) {
            nvMoi.setMatKhau("123456"); 
        }

        return nguoiDungRepo.save(nvMoi);
    }

    // [BỔ SUNG IMPLEMENT] Tìm kiếm và lọc người dùng
    public List<NguoiDungEntity> timKiemNguoiDung(String vaiTro, String keyword) {
        List<NguoiDungEntity> allUsers = nguoiDungRepo.findAll();
        
        return allUsers.stream()
            .filter(u -> vaiTro == null || u.getVaiTro().equalsIgnoreCase(vaiTro))
            .filter(u -> keyword == null || 
                        u.getHoTen().toLowerCase().contains(keyword.toLowerCase()) ||
                        u.getCccd().contains(keyword))
            .collect(Collectors.toList());
    }

    // [BỔ SUNG IMPLEMENT] Cập nhật thông tin
    public NguoiDungEntity capNhatThongTin(Long id, NguoiDungEntity dataMoi) {
        NguoiDungEntity user = nguoiDungRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        
        if (dataMoi.getHoTen() != null) user.setHoTen(dataMoi.getHoTen());
        if (dataMoi.getSdt() != null) user.setSdt(dataMoi.getSdt());
        if (dataMoi.getEmail() != null) user.setEmail(dataMoi.getEmail());
        if (dataMoi.getDiaChi() != null) user.setDiaChi(dataMoi.getDiaChi());
        
        return nguoiDungRepo.save(user);
    }

    // [BỔ SUNG IMPLEMENT] Phê duyệt đăng ký
    public void pheDuyetTaiKhoan(Long id) {
        NguoiDungEntity user = nguoiDungRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản!"));
        user.setHoatDong(true); // Kích hoạt tài khoản
        nguoiDungRepo.save(user);
    }

    @Override
    public void khoaTaiKhoan(Long id) {
        NguoiDungEntity user = nguoiDungRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        user.setHoatDong(false);
        nguoiDungRepo.save(user);
    }

    @Override
    public void xoaNguoiDung(Long id) {
        if (!nguoiDungRepo.existsById(id)) throw new RuntimeException("Không tìm thấy user!");
        nguoiDungRepo.deleteById(id);
    }

    // --- LOGIC QUẢN LÝ ĐẤT ĐAI (Import Excel) ---

    @Override
    public String importDuLieuDatDai(MultipartFile file) throws IOException {
        List<ThuaDatEntity> danhSachDat = new ArrayList<>();
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 
                ThuaDatEntity dat = new ThuaDatEntity();
                
                // Mapping: 0=SoTo, 1=SoThua, 2=DiaChi, 3=KhuVuc, 4=DienTich, 5=LoaiDat, 6=IDChu
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
    public void xoaThuaDat(Long id) {
        if (!thuaDatRepo.existsById(id)) throw new RuntimeException("Không tìm thấy đất!");
        thuaDatRepo.deleteById(id);
    }
}