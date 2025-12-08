package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.BangGiaDatEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import com.thanglong.quanlythuedat.usecase.IAdminUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private IAdminUseCase adminUseCase;

    // API 1: POST /api/admin/banggia
    // Chức năng: Cập nhật giá đất (Admin nhập giá mới -> Hệ thống lưu lại)
    @PostMapping("/banggia")
    public ResponseEntity<?> capNhatGiaDat(@RequestBody BangGiaDatEntity bangGia) {
        try {
            BangGiaDatEntity result = adminUseCase.capNhatBangGiaDat(bangGia);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API 2: POST /api/admin/tao-can-bo
    // Chức năng: Tạo tài khoản cho nhân viên thuế mới
    @PostMapping("/tao-can-bo")
    public ResponseEntity<?> taoCanBo(@RequestBody NguoiDungEntity canBo) {
        try {
            NguoiDungEntity result = adminUseCase.taoTaiKhoanCanBo(canBo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}