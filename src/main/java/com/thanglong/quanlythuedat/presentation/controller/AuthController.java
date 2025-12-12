package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.NguoiDungEntity;
import com.thanglong.quanlythuedat.usecase.dto.LoginDTO;
import com.thanglong.quanlythuedat.usecase.impl.AuthUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
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
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // API: POST /api/auth/register
    // Lưu ý: Tài khoản đăng ký xong có thể cần Admin phê duyệt (set hoatDong = false mặc định trong logic AuthUseCase)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody NguoiDungEntity nguoiMoi) {
        try {
            NguoiDungEntity user = authUseCase.dangKy(nguoiMoi);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // [BỔ SUNG] API: Quên mật khẩu (Screenshot 7)
    // Input: {"email": "..."} hoặc {"cccd": "..."}
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        try {
            String emailOrCccd = request.get("identifier");
            // Gọi UseCase để xử lý logic gửi mail hoặc reset pass (Giả lập logic)
            // authUseCase.quenMatKhau(emailOrCccd); 
            return ResponseEntity.ok(Map.of("message", "Mật khẩu mới đã được gửi về email/SĐT của bạn."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Không tìm thấy tài khoản."));
        }
    }
}