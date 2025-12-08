package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.KhieuNaiEntity;
import com.thanglong.quanlythuedat.usecase.IQuanLyHoSoUseCase;
import com.thanglong.quanlythuedat.usecase.dto.HoSoInputDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hoso")
public class HoSoController {

    @Autowired
    private IQuanLyHoSoUseCase quanLyHoSoUseCase;

    // ---------------------------------------------------------
    // API 1: POST /api/hoso/nop-to-khai
    // Chức năng: Chủ đất nộp tờ khai thuế
    // ---------------------------------------------------------
    @PostMapping("/nop-to-khai")
    public ResponseEntity<HoSoOutputDTO> nopHoSo(@RequestBody HoSoInputDTO input) {
        HoSoOutputDTO result = quanLyHoSoUseCase.nopHoSoKhaiThue(input);
        return ResponseEntity.ok(result);
    }

    // ---------------------------------------------------------
    // API 2: GET /api/hoso/danh-sach
    // Chức năng: Cán bộ thuế xem danh sách tất cả hồ sơ (để duyệt)
    // ---------------------------------------------------------
    @GetMapping("/danh-sach")
    public ResponseEntity<?> xemDanhSach() {
        // Trả về danh sách JSON các hồ sơ có trong Database
        return ResponseEntity.ok(quanLyHoSoUseCase.layDanhSachHoSo());
    }

    // ---------------------------------------------------------
    // API 3: POST /api/hoso/duyet
    // Chức năng: Cán bộ thuế Duyệt hoặc Từ chối hồ sơ
    // Tham số gửi lên: ?id=...&dongY=...&lyDo=...
    // ---------------------------------------------------------
    @PostMapping("/duyet")
    public ResponseEntity<String> duyetHoSo(
            @RequestParam Long id,           // Mã hồ sơ cần duyệt
            @RequestParam boolean dongY,     // true = Duyệt, false = Từ chối
            @RequestParam(required = false) String lyDo // Lý do (không bắt buộc)
    ) {
        
        String ketQua = quanLyHoSoUseCase.duyetHoSo(id, dongY, lyDo);
        return ResponseEntity.ok(ketQua);
    }
    @PostMapping("/khieu-nai")
    public ResponseEntity<?> guiKhieuNai(@RequestBody KhieuNaiEntity khieuNai) {
        // Gọi hàm guiKhieuNai trong UseCase (Bạn nhớ thêm hàm này vào Interface IQuanLyHoSoUseCase nhé)
        return ResponseEntity.ok("Đã gửi khiếu nại thành công");
    }
}