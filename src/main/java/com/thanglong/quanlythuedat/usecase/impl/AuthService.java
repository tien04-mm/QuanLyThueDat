package com.thanglong.quanlythuedat.usecase.impl;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.UserEntity;
import com.thanglong.quanlythuedat.infrastructure.repository.jpa.UserRepository;
import com.thanglong.quanlythuedat.usecase.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity login(LoginDTO loginRequest) {
        // 1. Find user by citizen ID
        String citizenIdInput = loginRequest.getCitizenId();
        
        UserEntity user = userRepository.findByCitizenId(citizenIdInput)
                .orElseThrow(() -> new RuntimeException("Invalid citizen ID or account does not exist!"));
        
        // 2. Verify password
        if (user.getPassword() == null || !user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Login failed: Invalid password!");
        }
        
        // 3. Check account status (active)
        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new RuntimeException("This account has been locked due to violation of regulations!");
        }

        return user;
    }

    public UserEntity register(UserEntity newUser) {
        // Check if citizen ID already exists
        if (userRepository.existsByCitizenId(newUser.getCitizenId())) {
            throw new RuntimeException("This citizen ID has already been registered!");
        }

        // Set default values
        newUser.setRole("LAND_OWNER");
        newUser.setIsActive(true); 
        
        // Set default password if empty
        if (newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
             newUser.setPassword("123456");
        }

        return userRepository.save(newUser);
    }
}
