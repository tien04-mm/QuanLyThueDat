package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.*;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.*;
import com.thanglong.quanlythuedat.usecase.IAdminUseCase;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminUseCase implements IAdminUseCase {

    @Autowired private JpaBangGiaDatRepo landPriceFrameRepo;
    @Autowired private JpaNguoiDungRepo userRepo;
    @Autowired private JpaThuaDatRepo landPlotRepo;

    @Override
    public LandPriceFrameEntity updateLandPriceFrame(LandPriceFrameEntity newPriceFrame) {
        Optional<LandPriceFrameEntity> existing = landPriceFrameRepo.findByFiscalYearAndZoneAreaAndLandTypeCode(
                newPriceFrame.getFiscalYear(), newPriceFrame.getZoneArea(), newPriceFrame.getLandTypeCode());
        
        if (existing.isPresent()) {
            LandPriceFrameEntity oldPriceFrame = existing.get();
            oldPriceFrame.setPricePerSquareMeter(newPriceFrame.getPricePerSquareMeter());
            return landPriceFrameRepo.save(oldPriceFrame);
        } else {
            return landPriceFrameRepo.save(newPriceFrame);
        }
    }

    @Override
    public UserEntity createOfficerAccount(UserEntity newOfficer) {
        // Check if citizen ID already exists
        if (userRepo.existsByCitizenId(newOfficer.getCitizenId())) {
            throw new RuntimeException("This citizen ID already exists in the system!");
        }

        newOfficer.setRole("OFFICER");
        newOfficer.setIsActive(true);
        
        if (newOfficer.getPassword() == null || newOfficer.getPassword().isEmpty()) {
            newOfficer.setPassword("123456"); 
        }
        return userRepo.save(newOfficer);
    }

    @Override
    public String importLandData(MultipartFile file) throws IOException {
        List<LandPlotEntity> landPlotList = new ArrayList<>();
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                LandPlotEntity landPlot = new LandPlotEntity();
                landPlot.setMapSheetNumber(getCellValue(row, 0));
                landPlot.setPlotNumber(getCellValue(row, 1));
                landPlot.setAddress(getCellValue(row, 2));
                landPlot.setZoneArea(getCellValue(row, 3));
                Cell cellArea = row.getCell(4);
                if (cellArea != null) landPlot.setOriginalArea(cellArea.getNumericCellValue());
                landPlot.setLandTypeCode(getCellValue(row, 5));
                
                Cell cellOwner = row.getCell(6);
                if (cellOwner != null) landPlot.setOwnerId((long) cellOwner.getNumericCellValue());
                
                landPlotList.add(landPlot);
            }
        }
        if (!landPlotList.isEmpty()) {
            landPlotRepo.saveAll(landPlotList);
            return "Import successful: " + landPlotList.size() + " land plots imported!";
        } else {
            return "File is empty or data could not be read!";
        }
    }
    
    private String getCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> "";
        };
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) throw new RuntimeException("User not found!");
        userRepo.deleteById(id);
    }

    @Override
    public void deleteLandPlot(Long id) {
        if (!landPlotRepo.existsById(id)) throw new RuntimeException("Land plot not found!");
        landPlotRepo.deleteById(id);
    }

    @Override
    public void lockUserAccount(Long id) {
        UserEntity user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        user.setIsActive(false);
        userRepo.save(user);
    }
}
