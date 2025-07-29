package com.VirtualBankingSystem.AccountService.Service;

import java.security.SecureRandom;

public class UtilityService {
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandom20DigitNumber() {
        StringBuilder sb = new StringBuilder(20);
        sb.append(random.nextInt(9) + 1);
        for (int i = 1; i < 20; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private UtilityService() {
    }
}
