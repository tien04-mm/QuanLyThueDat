package com.thanglong.quanlythuedat.usecase;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.LandPriceFrameEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IAdminUseCase {
    LandPriceFrameEntity updateLandPriceFrame(LandPriceFrameEntity newPriceFrame);
    
    UserEntity createOfficerAccount(UserEntity newOfficer);
    
    String importLandData(MultipartFile file) throws IOException;
    
    void deleteUser(Long id);
    
    void deleteLandPlot(Long id);
    
    void lockUserAccount(Long id);
}
