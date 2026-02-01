package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.LandPlotEntity;
import com.thanglong.quanlythuedat.usecase.IPropertyDeclarationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/land-plots")
public class LandPlotController {

    @Autowired
    private IPropertyDeclarationUseCase propertyDeclarationUseCase;

    // API: GET /api/land-plots/my-land-plots?ownerId=3
    // Function: Land owner views list of land plots they own
    @GetMapping("/my-land-plots")
    public ResponseEntity<List<LandPlotEntity>> getUserLandPlots(@RequestParam Long ownerId) {
        List<LandPlotEntity> landPlotList = propertyDeclarationUseCase.getUserLandPlots(ownerId);
        return ResponseEntity.ok(landPlotList);
    }
}
