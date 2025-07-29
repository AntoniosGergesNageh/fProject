package com.VirtualBankingSystem.BFFService.Controller;

import com.VirtualBankingSystem.BFFService.DTO.*;
import com.VirtualBankingSystem.BFFService.Service.BFFService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/bff")
public class BFFController {
    @Autowired
    private BFFService bffService;

    @RequestMapping("/dashboard/{userId}")
    public ResponseEntity<UserResponseDTO> getDashBoard(@NotNull @PathVariable(value = "userId") UUID userId){
        return ResponseEntity.ok(bffService.getUserData(userId));
    }
    
    @PostMapping("/transactions/transfer/initiation")
    public ResponseEntity<TransferInitiationResponseDTO> initiateTransfer(
            @Valid @RequestBody TransferInitiationRequestDTO request) {
        return ResponseEntity.ok(bffService.initiateTransfer(request));
    }
    
    @PostMapping("/transactions/transfer/execution")
    public ResponseEntity<TransferExecutionResponseDTO> executeTransfer(
            @Valid @RequestBody TransferExecutionRequestDTO request) {
        return ResponseEntity.ok(bffService.executeTransfer(request));
    }
}
