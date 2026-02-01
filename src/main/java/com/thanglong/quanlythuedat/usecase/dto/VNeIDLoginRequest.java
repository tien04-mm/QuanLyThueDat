package com.thanglong.quanlythuedat.usecase.dto;

import lombok.Data;

@Data
public class VNeIDLoginRequest {
    private String citizenId; // Maps to citizen_id column in database
    private String password;
}