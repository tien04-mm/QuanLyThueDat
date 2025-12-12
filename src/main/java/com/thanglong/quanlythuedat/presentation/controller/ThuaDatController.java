package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.ThuaDatEntity;
import com.thanglong.quanlythuedat.usecase.IThuaDatUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/thuadat")
@CrossOrigin(origins = "*") // Cho phép Frontend gọi
public class ThuaDatController {

    // SỬA: Đổi từ QuanLyHoSoUseCase sang ThuaDatUseCase cho đúng nghiệp vụ
    @Autowired
    private IThuaDatUseCase thuaDatUseCase;

    // API: GET /api/thuadat/cua-toi?maChuSoHuu=3
    // Chức năng: Chủ đất xem danh sách đất mình sở hữu (Screenshot 5 - Xem kết quả hiển thị)
    @GetMapping("/cua-toi")
    public ResponseEntity<List<ThuaDatEntity>> traCuuDatCuaToi(@RequestParam Long maChuSoHuu) {
        return ResponseEntity.ok(thuaDatUseCase.traCuuDatCuaToi(maChuSoHuu));
    }

    // [BỔ SUNG] API: GET /api/thuadat/tra-cuu
    // Chức năng: Nhập mã thửa đất (Số tờ, Số thửa) để xem thông tin (Screenshot 5 - Nhập mã thửa đất)
    // Ví dụ: /api/thuadat/tra-cuu?soTo=10&soThua=25
    @GetMapping("/tra-cuu")
    public ResponseEntity<?> traCuuThongTinDat(
            @RequestParam String soTo,
            @RequestParam String soThua) {
        try {
            ThuaDatEntity dat = thuaDatUseCase.timThuaDat(soTo, soThua);
            return ResponseEntity.ok(dat);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Không tìm thấy thửa đất này!"));
        }
    }
}