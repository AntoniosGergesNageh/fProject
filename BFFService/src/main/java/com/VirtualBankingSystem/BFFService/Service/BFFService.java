package com.VirtualBankingSystem.BFFService.Service;

import com.VirtualBankingSystem.BFFService.DTO.UserResponseDTO;
import com.VirtualBankingSystem.BFFService.Service.RequestsAggregation.DefaultAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BFFService {
    private final DefaultAggregator defaultAggregator;

    public BFFService(@Autowired DefaultAggregator defaultAggregator) {
        this.defaultAggregator = defaultAggregator;
    }

    public UserResponseDTO getUserData(UUID userId) {
        return defaultAggregator.aggregateUserData(userId);
    }
}
