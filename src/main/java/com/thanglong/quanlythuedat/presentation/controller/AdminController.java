package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import com.thanglong.quanlythuedat.usecase.IAdminUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private IAdminUseCase adminUseCase;

    @PostMapping("/banggia")
    public ResponseEntity<?> capNhatGiaDat(@RequestBody BangGiaDatEntity bangGia) {
        try {
            return ResponseEntity.ok(adminUseCase.capNhatBangGiaDat(bangGia));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/tao-can-bo")
    public ResponseEntity<?> taoCanBo(@RequestBody NguoiDungEntity canBo) {
        try {
            return ResponseEntity.ok(adminUseCase.taoTaiKhoanCanBo(canBo));
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

    @DeleteMapping("/nguoi-dung/{id}")
    public ResponseEntity<String> xoaNguoiDung(@PathVariable Long id) {
        try {
            adminUseCase.xoaNguoiDung(id);
            return ResponseEntity.ok("Đã xóa người dùng thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi xóa: " + e.getMessage());
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

    // [MỚI] API KHÓA TÀI KHOẢN
    @PutMapping("/nguoi-dung/{id}/khoa")
    public ResponseEntity<String> khoaTaiKhoan(@PathVariable Long id) {
        try {
            adminUseCase.khoaTaiKhoan(id);
            return ResponseEntity.ok("Đã khóa tài khoản thành công! Người dùng sẽ không thể đăng nhập.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }
}