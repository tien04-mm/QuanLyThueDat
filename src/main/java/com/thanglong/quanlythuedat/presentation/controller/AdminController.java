package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.ThuaDatEntity;
import com.thanglong.quanlythuedat.usecase.IAdminUseCase;
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

    @Autowired
    private IAdminUseCase adminUseCase;

    // --- QUẢN LÝ ĐẤT ĐAI ---

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
            String ketQua = adminUseCase.importDuLieuDatDai(file);
            return ResponseEntity.ok(ketQua);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi Import: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/thua-dat/{id}")
    public ResponseEntity<String> xoaThuaDat(@PathVariable Long id) {
        try {
            adminUseCase.xoaThuaDat(id);
            return ResponseEntity.ok("Đã xóa thửa đất thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi xóa: " + e.getMessage());
        }
    }

    // --- QUẢN LÝ NGƯỜI DÙNG (ADMIN & USER) ---

    // API Tạo nhân viên (Admin -> Cán bộ / QL Đất đai)
    @PostMapping("/tao-nhan-vien")
    public ResponseEntity<?> taoNhanVien(@RequestBody NguoiDungEntity nhanVien) {
        try {
            return ResponseEntity.ok(adminUseCase.taoTaiKhoanNhanVien(nhanVien));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // [BỔ SUNG] API Xem danh sách & Tìm kiếm (Screenshot 1, 4, 8)
    // Hỗ trợ lọc theo vai trò (ví dụ: ?vaiTro=CHU_DAT) hoặc từ khóa tên
    @GetMapping("/nguoi-dung")
    public ResponseEntity<List<NguoiDungEntity>> layDanhSachNguoiDung(
            @RequestParam(required = false) String vaiTro,
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(adminUseCase.timKiemNguoiDung(vaiTro, keyword));
    }

    // [BỔ SUNG] API Cập nhật thông tin người dùng (Screenshot 1, 8)
    @PutMapping("/nguoi-dung/{id}")
    public ResponseEntity<?> capNhatThongTin(@PathVariable Long id, @RequestBody NguoiDungEntity dataMoi) {
        try {
            return ResponseEntity.ok(adminUseCase.capNhatThongTin(id, dataMoi));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // [BỔ SUNG] API Phê duyệt đăng ký (Screenshot 8)
    // Admin duyệt tài khoản Chủ đất mới đăng ký (chuyển hoatDong = true)
    @PutMapping("/nguoi-dung/{id}/phe-duyet")
    public ResponseEntity<?> pheDuyetTaiKhoan(@PathVariable Long id) {
        try {
            adminUseCase.pheDuyetTaiKhoan(id);
            return ResponseEntity.ok(Map.of("message", "Đã phê duyệt tài khoản thành công!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/nguoi-dung/{id}/khoa")
    public ResponseEntity<String> khoaTaiKhoan(@PathVariable Long id) {
        try {
            adminUseCase.khoaTaiKhoan(id);
            return ResponseEntity.ok("Đã khóa tài khoản thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @DeleteMapping("/nguoi-dung/{id}")
    public ResponseEntity<String> xoaNguoiDung(@PathVariable Long id) {
        try {
            adminUseCase.xoaNguoiDung(id);
            return ResponseEntity.ok("Đã xóa người dùng thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi xóa: " + e.getMessage());
        }
    }
    @Autowired
    private IThuaDatUseCase thuaDatUseCase; // Inject thêm cái này vào AdminController

    @PutMapping("/thua-dat/{id}")
    public ResponseEntity<?> capNhatThuaDat(@PathVariable Long id, @RequestBody ThuaDatEntity datMoi) {
        try {
            return ResponseEntity.ok(thuaDatUseCase.capNhatThongTinDat(id, datMoi));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}