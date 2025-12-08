package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import com.thanglong.quanlythuedat.usecase.dto.LoginDTO;
import com.thanglong.quanlythuedat.usecase.impl.AuthUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthUseCase authUseCase;

    // API: POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
        try {
            NguoiDungEntity user = authUseCase.dangNhap(loginRequest);
            return ResponseEntity.ok(user); // Trả về thông tin người dùng nếu đúng
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API: POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody NguoiDungEntity nguoiMoi) {
        try {
            NguoiDungEntity user = authUseCase.dangKy(nguoiMoi);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}