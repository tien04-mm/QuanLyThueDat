package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.usecase.IQuanLyHoSoUseCase;
import com.thanglong.quanlythuedat.usecase.dto.BaoCaoThongKeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/thongke")
@CrossOrigin(origins = "*") // Bổ sung để Frontend gọi không bị lỗi CORS
public class ThongKeController {

    @Autowired
    private IQuanLyHoSoUseCase quanLyHoSoUseCase;

    // API: GET /api/thongke/baocao
    // Cập nhật: Hỗ trợ lọc theo năm và khu vực (Optional)
    // Ví dụ: /api/thongke/baocao?nam=2025&khuVuc=Quan1
    @GetMapping("/baocao")
    public ResponseEntity<BaoCaoThongKeDTO> xemBaoCao(
            @RequestParam(required = false) Integer nam,
            @RequestParam(required = false) String khuVuc
    ) {
        // Gọi hàm mới có tham số (đã cập nhật ở bước trước)
        BaoCaoThongKeDTO baoCao = quanLyHoSoUseCase.layBaoCaoThongKe(nam, khuVuc);
        return ResponseEntity.ok(baoCao);
    }
}