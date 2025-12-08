package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.usecase.IQuanLyHoSoUseCase;
import com.thanglong.quanlythuedat.usecase.dto.BaoCaoThongKeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/thongke")
public class ThongKeController {

    @Autowired
    private IQuanLyHoSoUseCase quanLyHoSoUseCase;

    // API: GET /api/thongke/baocao
    // Chức năng: Cán bộ thuế/Admin xem dashboard thống kê
    @GetMapping("/baocao")
    public ResponseEntity<BaoCaoThongKeDTO> xemBaoCao() {
        BaoCaoThongKeDTO baoCao = quanLyHoSoUseCase.layBaoCaoThongKe();
        return ResponseEntity.ok(baoCao);
    }
}