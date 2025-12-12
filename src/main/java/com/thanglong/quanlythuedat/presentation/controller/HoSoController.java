package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.HoSoEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.KhieuNaiEntity;
import com.thanglong.quanlythuedat.usecase.IQuanLyHoSoUseCase;
import com.thanglong.quanlythuedat.usecase.dto.HoSoInputDTO;
import com.thanglong.quanlythuedat.usecase.dto.HoSoOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hoso")
@CrossOrigin(origins = "*")
public class HoSoController {

    @Autowired
    private IQuanLyHoSoUseCase quanLyHoSoUseCase;

    @PostMapping("/nop-to-khai")
    public ResponseEntity<HoSoOutputDTO> nopHoSo(@RequestBody HoSoInputDTO input) {
        return ResponseEntity.ok(quanLyHoSoUseCase.nopHoSoKhaiThue(input));
    }

    @GetMapping("/danh-sach")
    public ResponseEntity<List<HoSoEntity>> xemDanhSach() {
        return ResponseEntity.ok(quanLyHoSoUseCase.layDanhSachHoSo());
    }

    // [MỚI] Xem chi tiết hồ sơ (Screenshot 6)
    @GetMapping("/{id}")
    public ResponseEntity<?> xemChiTiet(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(quanLyHoSoUseCase.layChiTietHoSo(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/duyet")
    public ResponseEntity<String> duyetHoSo(
            @RequestParam Long id,
            @RequestParam boolean dongY,
            @RequestParam(required = false) String lyDo) {
        return ResponseEntity.ok(quanLyHoSoUseCase.duyetHoSo(id, dongY, lyDo));
    }

    // [MỚI] Thanh toán thuế (Screenshot 2)
    @PostMapping("/{id}/thanh-toan")
    public ResponseEntity<?> thanhToan(@PathVariable Long id) {
        try {
            quanLyHoSoUseCase.thanhToanThue(id);
            return ResponseEntity.ok(Map.of("message", "Thanh toán thành công! Hồ sơ đã hoàn tất."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // [MỚI] Xuất Excel (Screenshot 6 - Xuất dữ liệu hồ sơ)
    @GetMapping("/xuat-excel")
    public ResponseEntity<InputStreamResource> xuatExcel() {
        ByteArrayInputStream in = quanLyHoSoUseCase.xuatBaoCaoExcel();
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=danh_sach_hoso.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }

    @PostMapping("/khieu-nai")
    public ResponseEntity<?> guiKhieuNai(@RequestBody KhieuNaiEntity khieuNai) {
        return ResponseEntity.ok(quanLyHoSoUseCase.guiKhieuNai(khieuNai.getMaHoSo(), khieuNai.getMaNguoiGui(), khieuNai.getNoiDung()));
    }
}