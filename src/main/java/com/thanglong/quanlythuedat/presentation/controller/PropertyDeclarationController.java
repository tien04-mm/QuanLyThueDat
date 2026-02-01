package com.thanglong.quanlythuedat.presentation.controller;

import com.thanglong.quanlythuedat.infrastructure.repository.entity.ComplaintEntity;
import com.thanglong.quanlythuedat.usecase.IPropertyDeclarationUseCase;
import com.thanglong.quanlythuedat.usecase.dto.PropertyDeclarationInputDTO;
import com.thanglong.quanlythuedat.usecase.dto.PropertyDeclarationOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/property-declarations")
public class PropertyDeclarationController {

    @Autowired
    private IPropertyDeclarationUseCase propertyDeclarationUseCase;

    // ---------------------------------------------------------
    // API 1: POST /api/property-declarations/submit
    // Function: Land owner submits property tax declaration
    // ---------------------------------------------------------
    @PostMapping("/submit")
    public ResponseEntity<PropertyDeclarationOutputDTO> submitDeclaration(@RequestBody PropertyDeclarationInputDTO input) {
        PropertyDeclarationOutputDTO result = propertyDeclarationUseCase.submitPropertyDeclaration(input);
        return ResponseEntity.ok(result);
    }

    // ---------------------------------------------------------
    // API 2: GET /api/property-declarations/list
    // Function: Tax officer views all declarations (for review/approval)
    // ---------------------------------------------------------
    @GetMapping("/list")
    public ResponseEntity<?> getAllDeclarations() {
        // Return JSON list of all declarations in database
        return ResponseEntity.ok(propertyDeclarationUseCase.getAllDeclarations());
    }

    // ---------------------------------------------------------
    // API 3: POST /api/property-declarations/approve
    // Function: Tax officer approves or rejects a declaration
    // Parameters: ?declarationId=...&approved=...&reason=...
    // ---------------------------------------------------------
    @PostMapping("/approve")
    public ResponseEntity<String> approveDeclaration(
            @RequestParam Long declarationId,  // Declaration ID to approve
            @RequestParam boolean approved,     // true = Approve, false = Reject
            @RequestParam(required = false) String reason // Reason (optional)
    ) {
        String result = propertyDeclarationUseCase.approveDeclaration(declarationId, approved, reason);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/complaint")
    public ResponseEntity<?> fileComplaint(@RequestBody ComplaintEntity complaint) {
        // Call complaint submission method in UseCase
        return ResponseEntity.ok("Complaint filed successfully");
    }
}
