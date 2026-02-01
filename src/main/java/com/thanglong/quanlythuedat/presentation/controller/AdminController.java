package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.LandPriceFrameEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.UserEntity;
import com.thanglong.quanlythuedat.usecase.IAdminUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private IAdminUseCase adminUseCase;

    @PostMapping("/land-price-frame")
    public ResponseEntity<?> updateLandPriceFrame(@RequestBody LandPriceFrameEntity priceFrame) {
        try {
            return ResponseEntity.ok(adminUseCase.updateLandPriceFrame(priceFrame));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create-officer")
    public ResponseEntity<?> createOfficerAccount(@RequestBody UserEntity officer) {
        try {
            return ResponseEntity.ok(adminUseCase.createOfficerAccount(officer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/import-land-data", consumes = {"multipart/form-data"})
    public ResponseEntity<String> importLandDataFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            String result = adminUseCase.importLandData(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Import error: " + e.getMessage());
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            adminUseCase.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Delete error: " + e.getMessage());
        }
    }

    @DeleteMapping("/land-plots/{id}")
    public ResponseEntity<String> deleteLandPlot(@PathVariable Long id) {
        try {
            adminUseCase.deleteLandPlot(id);
            return ResponseEntity.ok("Land plot deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Delete error: " + e.getMessage());
        }
    }

    // [NEW] API TO LOCK/DEACTIVATE USER ACCOUNT
    @PutMapping("/users/{id}/lock")
    public ResponseEntity<String> lockUserAccount(@PathVariable Long id) {
        try {
            adminUseCase.lockUserAccount(id);
            return ResponseEntity.ok("User account has been locked successfully! User cannot login.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}