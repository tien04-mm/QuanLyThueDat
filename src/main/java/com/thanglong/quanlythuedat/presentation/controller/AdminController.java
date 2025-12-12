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

    @Autowired
    private IAdminUseCase adminUseCase;

    @Autowired
    private IThuaDatUseCase thuaDatUseCase;

    @Autowired
    private IQuanLyHoSoUseCase quanLyHoSoUseCase; // Inject thêm để lấy số liệu báo cáo

    // ========================================================================
    // 1. NHÓM CHỨC NĂNG QUẢN LÝ ĐẤT ĐAI & BẢNG GIÁ
    // ========================================================================

    // Cập nhật bảng giá đất (để tính thuế)
    @PostMapping("/banggia")
    public ResponseEntity<?> capNhatGiaDat(@RequestBody BangGiaDatEntity bangGia) {
        try {
            return ResponseEntity.ok(adminUseCase.capNhatBangGiaDat(bangGia));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Import dữ liệu đất đai từ file Excel (Master Data)
    @PostMapping(value = "/import-dat-dai", consumes = {"multipart/form-data"})
    public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile file) {
        try {
            String ketQua = adminUseCase.importDuLieuDatDai(file);
            return ResponseEntity.ok(ketQua);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi Import: " + e.getMessage());
        }
    }

    // Cập nhật thông tin thửa đất thủ công (Sửa sai sót)
    @PutMapping("/thua-dat/{id}")
    public ResponseEntity<?> capNhatThuaDat(@PathVariable Long id, @RequestBody ThuaDatEntity datMoi) {
        try {
            return ResponseEntity.ok(thuaDatUseCase.capNhatThongTinDat(id, datMoi));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Xóa thửa đất
    @DeleteMapping("/thua-dat/{id}")
    public ResponseEntity<String> xoaThuaDat(@PathVariable Long id) {
        try {
            adminUseCase.xoaThuaDat(id);
            return ResponseEntity.ok("Đã xóa thửa đất thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi xóa: " + e.getMessage());
        }
    }

    // ========================================================================
    // 2. NHÓM CHỨC NĂNG QUẢN LÝ NGƯỜI DÙNG (ADMIN & USER)
    // ========================================================================

    // Tạo tài khoản nội bộ (Cán bộ thuế / Quản lý đất đai)
    @PostMapping("/tao-nhan-vien")
    public ResponseEntity<?> taoNhanVien(@RequestBody NguoiDungEntity nhanVien) {
        try {
            return ResponseEntity.ok(adminUseCase.taoTaiKhoanNhanVien(nhanVien));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Phê duyệt tài khoản người dân mới đăng ký
    @PutMapping("/nguoi-dung/{id}/phe-duyet")
    public ResponseEntity<?> pheDuyetTaiKhoan(@PathVariable Long id) {
        try {
            adminUseCase.pheDuyetTaiKhoan(id);
            return ResponseEntity.ok(Map.of("message", "Đã phê duyệt tài khoản thành công!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Tìm kiếm và xem danh sách người dùng (Lọc theo vai trò/tên)
    @GetMapping("/nguoi-dung")
    public ResponseEntity<List<NguoiDungEntity>> layDanhSachNguoiDung(
            @RequestParam(required = false) String vaiTro,
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(adminUseCase.timKiemNguoiDung(vaiTro, keyword));
    }

    // Cập nhật thông tin người dùng (SĐT, Email, Địa chỉ)
    @PutMapping("/nguoi-dung/{id}")
    public ResponseEntity<?> capNhatThongTin(@PathVariable Long id, @RequestBody NguoiDungEntity dataMoi) {
        try {
            return ResponseEntity.ok(adminUseCase.capNhatThongTin(id, dataMoi));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Khóa tài khoản (Khi vi phạm)
    @PutMapping("/nguoi-dung/{id}/khoa")
    public ResponseEntity<String> khoaTaiKhoan(@PathVariable Long id) {
        try {
            adminUseCase.khoaTaiKhoan(id);
            return ResponseEntity.ok("Đã khóa tài khoản thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    // Xóa người dùng vĩnh viễn
    @DeleteMapping("/nguoi-dung/{id}")
    public ResponseEntity<String> xoaNguoiDung(@PathVariable Long id) {
        try {
            adminUseCase.xoaNguoiDung(id);
            return ResponseEntity.ok("Đã xóa người dùng thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi xóa: " + e.getMessage());
        }
    }

    // ========================================================================
    // 3. NHÓM CHỨC NĂNG BÁO CÁO - THỐNG KÊ (MỚI)
    // ========================================================================

    // Xem báo cáo thống kê chi tiết (Tổng thu, Nợ thuế, Số lượng hồ sơ...)
    // Hỗ trợ lọc theo Năm và Khu vực
    @GetMapping("/thong-ke")
    public ResponseEntity<?> xemBaoCaoThongKe(
            @RequestParam(required = false) Integer nam,
            @RequestParam(required = false) String khuVuc) {
        try {
            // Gọi sang UseCase Hồ sơ để lấy số liệu thực tế
            return ResponseEntity.ok(quanLyHoSoUseCase.layBaoCaoThongKe(nam, khuVuc));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}