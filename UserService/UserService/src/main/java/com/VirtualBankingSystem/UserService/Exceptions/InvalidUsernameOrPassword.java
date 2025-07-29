package com.VirtualBankingSystem.UserService.Exceptions;

public class InvalidUsernameOrPassword extends RuntimeException {
    public InvalidUsernameOrPassword(String message) {
        super(message);
    }
}
