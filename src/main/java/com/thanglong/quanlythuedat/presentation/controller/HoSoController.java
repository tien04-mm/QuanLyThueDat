package com.thanglong.quanlythuedat.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.HoSoEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.KhieuNaiEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.NhatKyXuLyEntity;
import com.thanglong.quanlythuedat.usecase.IQuanLyHoSoUseCase;
import com.thanglong.quanlythuedat.usecase.dto.HoSoInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hoso")
@CrossOrigin(origins = "*")
public class HoSoController {

    @Autowired
    private IQuanLyHoSoUseCase quanLyHoSoUseCase;

    // Nộp hồ sơ
    @PostMapping(value = "/nop-to-khai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> nopHoSo(
            @RequestPart("data") String dataJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            HoSoInputDTO input = mapper.readValue(dataJson, HoSoInputDTO.class);
            return ResponseEntity.ok(quanLyHoSoUseCase.nopHoSoKhaiThue(input, file));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // [QUAN TRỌNG] Xem lịch sử xử lý (Trả về List Entity)
    @GetMapping("/{id}/lich-su-xu-ly")
    public ResponseEntity<List<NhatKyXuLyEntity>> xemLichSuXuLy(@PathVariable Long id) {
        return ResponseEntity.ok(quanLyHoSoUseCase.xemLichSuXuLy(id));
    }

    @GetMapping("/tra-cuu/{maHoSo}")
    public ResponseEntity<?> traCuuTrangThai(@PathVariable Long maHoSo) {
        try {
            return ResponseEntity.ok(quanLyHoSoUseCase.layChiTietHoSo(maHoSo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Không tìm thấy hồ sơ!"));
        }
    }

    @PostMapping("/duyet")
    public ResponseEntity<String> duyetHoSo(@RequestParam Long id, @RequestParam boolean dongY, @RequestParam String lyDo) {
        return ResponseEntity.ok(quanLyHoSoUseCase.duyetHoSo(id, dongY, lyDo));
    }

    @PostMapping("/{id}/thanh-toan")
    public ResponseEntity<?> thanhToan(@PathVariable Long id) {
        try {
            quanLyHoSoUseCase.thanhToanThue(id);
            return ResponseEntity.ok(Map.of("message", "Thanh toán thành công!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/khieu-nai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> guiKhieuNai(@RequestPart("data") String dataJson, @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            KhieuNaiEntity knData = mapper.readValue(dataJson, KhieuNaiEntity.class);
            return ResponseEntity.ok(quanLyHoSoUseCase.guiKhieuNai(knData, file));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/danh-sach")
    public ResponseEntity<List<HoSoEntity>> xemDanhSach() {
        return ResponseEntity.ok(quanLyHoSoUseCase.layDanhSachHoSo());
    }
}