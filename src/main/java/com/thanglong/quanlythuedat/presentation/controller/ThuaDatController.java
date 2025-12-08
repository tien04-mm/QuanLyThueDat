package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.ThuaDatEntity;
import com.thanglong.quanlythuedat.usecase.IQuanLyHoSoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thuadat")
public class ThuaDatController {

    @Autowired
    private IQuanLyHoSoUseCase quanLyHoSoUseCase;

    // API: GET /api/thuadat/cua-toi?maChuSoHuu=3
    // Chức năng: Chủ đất xem danh sách đất mình sở hữu
    @GetMapping("/cua-toi")
    public ResponseEntity<List<ThuaDatEntity>> traCuuDatCuaToi(@RequestParam Long maChuSoHuu) {
        List<ThuaDatEntity> danhSachDat = quanLyHoSoUseCase.traCuuDatCuaToi(maChuSoHuu);
        return ResponseEntity.ok(danhSachDat);
    }
}