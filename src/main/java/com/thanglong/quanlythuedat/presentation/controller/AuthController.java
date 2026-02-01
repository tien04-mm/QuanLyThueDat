package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.UserEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.UserRepository;
import com.thanglong.quanlythuedat.usecase.dto.VNeIDLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
// [FIX] Use originPatterns instead of origins to avoid 500 Credential error
@CrossOrigin(originPatterns = "*") 
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // --- API 1: LOGIN (Using Citizen ID + Password) ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody VNeIDLoginRequest request) {
        // 1. Find user by citizen ID
        Optional<UserEntity> userOpt = userRepository.findByCitizenId(request.getCitizenId());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Citizen ID does not exist or account is not registered!"));
        }

        UserEntity user = userOpt.get();

        // 2. Verify password
        if (user.getPassword() == null || !user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid password!"));
        }

        // 3. Return user information + fake token
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("fullName", user.getFullName());
        response.put("citizenId", user.getCitizenId());
        response.put("role", user.getRole());
        response.put("token", "auth-token-" + System.currentTimeMillis());

        return ResponseEntity.ok(response);
    }

    // --- API 2: MOCK QR SCAN FOR MOBILE ---
    @PostMapping("/mock-qr-scan")
    public ResponseEntity<?> mockQrScan(@RequestParam String qrCode) {
        // Get first user for testing purposes (ensure DB is not empty)
        UserEntity mockUser = userRepository.findAll().stream().findFirst().orElse(null);
        
        if (mockUser != null) {
             return ResponseEntity.ok(Map.of(
                "status", "SUCCESS",
                "message", "Device authentication successful",
                "userInfo", mockUser
            ));
        }
        return ResponseEntity.ok(Map.of("status", "SUCCESS", "message", "Database is empty but test passed (Test mode)"));
    }
}