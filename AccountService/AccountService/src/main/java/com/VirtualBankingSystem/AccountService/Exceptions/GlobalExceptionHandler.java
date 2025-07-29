package com.VirtualBankingSystem.AccountService.Exceptions;

import com.VirtualBankingSystem.AccountService.DTO.Response.ErrorResponseDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccountNotFoundException(AccountNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(404,
                "Not Found",
                ex.getMessage() != null ? ex.getMessage() : "Account not found",
                List.of("Please check the account ID and try again."));

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotEnoughBalanceException(InsufficientBalanceException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(400,
                "Bad Request",
                ex.getMessage() != null ? ex.getMessage() : "Insufficient balance for the transaction",
                List.of("Please ensure that your account has enough balance to complete the transaction."));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(404,
                "Not Found",
                ex.getMessage() != null ? ex.getMessage() : "User not found",
                List.of("Please check the user ID and try again."));

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(400,
                "Bad Request",
                ex.getMessage() != null ? ex.getMessage() : "Invalid argument provided",
                List.of("Please check the provided arguments and try again."));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String fieldName = ex.getName();
        Object invalidValue = ex.getValue();

        ErrorResponseDTO error = new ErrorResponseDTO(
                400,
                "Bad Request",
                "Invalid value for '" + fieldName + "': " + invalidValue,
                List.of("Expected format: UUID")
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponseDTO error = new ErrorResponseDTO(
                400,
                "Bad Request",
                "Validation failed for one or more fields.",
                errorMessages
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequestException(BadRequestException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(400,
                "Bad Request",
                ex.getMessage() != null ? ex.getMessage() : "Bad request",
                List.of("Please check the request format and parameters."));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(500,
                "Internal Server Error",
                "An unexpected error occurred",
                List.of(ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred. Please try again later."));

        return ResponseEntity.status(500).body(errorResponse);
    }
}
