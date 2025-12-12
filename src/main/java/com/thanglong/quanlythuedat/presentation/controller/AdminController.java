package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.ThuaDatEntity;
import com.thanglong.quanlythuedat.usecase.IAdminUseCase;
import com.thanglong.quanlythuedat.usecase.IQuanLyHoSoUseCase;
import com.thanglong.quanlythuedat.usecase.IThuaDatUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    @Autowired private IAdminUseCase adminUseCase;
    @Autowired private IThuaDatUseCase thuaDatUseCase;
    @Autowired private IQuanLyHoSoUseCase quanLyHoSoUseCase;

    // ==========================================================
    // 1. QUẢN LÝ DỮ LIỆU ĐẤT ĐAI
    // ==========================================================

    @PostMapping("/banggia")
    public ResponseEntity<?> capNhatGiaDat(@RequestBody BangGiaDatEntity bangGia) {
        try {
            return ResponseEntity.ok(adminUseCase.capNhatBangGiaDat(bangGia));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/import-dat-dai", consumes = {"multipart/form-data"})
    public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(adminUseCase.importDuLieuDatDai(file));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    // [LƯU Ý] Hàm này dùng IThuaDatUseCase (Logic cập nhật đất lẻ)
    @PutMapping("/thua-dat/{id}")
    public ResponseEntity<?> capNhatThuaDat(@PathVariable Long id, @RequestBody ThuaDatEntity datMoi) {
        try {
            return ResponseEntity.ok(thuaDatUseCase.capNhatThongTinDat(id, datMoi));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/thua-dat/{id}")
    public ResponseEntity<String> xoaThuaDat(@PathVariable Long id) {
        try {
            adminUseCase.xoaThuaDat(id);
            return ResponseEntity.ok("Đã xóa!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ==========================================================
    // 2. QUẢN LÝ NGƯỜI DÙNG
    // ==========================================================

    @PostMapping("/tao-nhan-vien")
    public ResponseEntity<?> taoNhanVien(@RequestBody NguoiDungEntity nhanVien) {
        try {
            return ResponseEntity.ok(adminUseCase.taoTaiKhoanNhanVien(nhanVien));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/nguoi-dung/{id}/phe-duyet")
    public ResponseEntity<?> pheDuyet(@PathVariable Long id) {
        try {
            adminUseCase.pheDuyetTaiKhoan(id);
            return ResponseEntity.ok(Map.of("message", "Đã duyệt thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/nguoi-dung/{id}/khoa")
    public ResponseEntity<?> khoaTaiKhoan(@PathVariable Long id) {
        try {
            adminUseCase.khoaTaiKhoan(id);
            return ResponseEntity.ok(Map.of("message", "Đã khóa tài khoản"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/nguoi-dung/{id}")
    public ResponseEntity<?> xoaNguoiDung(@PathVariable Long id) {
        try {
            adminUseCase.xoaNguoiDung(id);
            return ResponseEntity.ok(Map.of("message", "Đã xóa người dùng"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/nguoi-dung")
    public ResponseEntity<List<NguoiDungEntity>> layDanhSach(
            @RequestParam(required = false) String vaiTro, 
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(adminUseCase.timKiemNguoiDung(vaiTro, keyword));
    }

    @PutMapping("/nguoi-dung/{id}")
    public ResponseEntity<?> capNhatUser(@PathVariable Long id, @RequestBody NguoiDungEntity data) {
        try {
            return ResponseEntity.ok(adminUseCase.capNhatThongTin(id, data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ==========================================================
    // 3. BÁO CÁO THỐNG KÊ
    // ==========================================================

    @GetMapping("/thong-ke")
    public ResponseEntity<?> xemBaoCao(
            @RequestParam(required = false) Integer nam, 
            @RequestParam(required = false) String khuVuc) {
        try {
            return ResponseEntity.ok(quanLyHoSoUseCase.layBaoCaoThongKe(nam, khuVuc));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}