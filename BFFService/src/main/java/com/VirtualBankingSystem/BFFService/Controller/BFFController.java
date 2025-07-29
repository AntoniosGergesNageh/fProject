package com.VirtualBankingSystem.BFFService.Controller;

import com.VirtualBankingSystem.BFFService.DTO.UserResponseDTO;
import com.VirtualBankingSystem.BFFService.Service.BFFService;
// Removed unused import for @NotBlank
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
