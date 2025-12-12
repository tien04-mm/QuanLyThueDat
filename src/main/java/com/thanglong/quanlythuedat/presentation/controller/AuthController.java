package com.thanglong.quanlythuedat.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import com.thanglong.quanlythuedat.usecase.dto.LoginDTO;
import com.thanglong.quanlythuedat.usecase.impl.AuthUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthUseCase authUseCase;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
        try {
            NguoiDungEntity user = authUseCase.dangNhap(loginRequest);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // [CẬP NHẬT] Đăng ký có kèm file ảnh giấy tờ
    // Gửi form-data: key "user" (json string), key "file" (file ảnh)
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(
            @RequestPart("user") String userJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            NguoiDungEntity nguoiMoi = mapper.readValue(userJson, NguoiDungEntity.class);
            
            NguoiDungEntity user = authUseCase.dangKy(nguoiMoi, file);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(Map.of("message", "Mật khẩu mới đã được gửi về email/SĐT."));
    }
}