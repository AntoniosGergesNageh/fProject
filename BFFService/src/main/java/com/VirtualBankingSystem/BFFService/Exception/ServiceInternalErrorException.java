package com.VirtualBankingSystem.BFFService.Exception;

public class ServiceInternalErrorException extends RuntimeException {
    public ServiceInternalErrorException(String message) {
        super(message);
    }
}
