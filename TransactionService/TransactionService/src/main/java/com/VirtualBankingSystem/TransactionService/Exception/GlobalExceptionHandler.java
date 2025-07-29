package com.VirtualBankingSystem.TransactionService.Exception;

import com.VirtualBankingSystem.TransactionService.DTO.Response.ErrorResponseDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                404,
                "Transaction Not Found",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalStateException(IllegalStateException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                400,
                "Bad Request",
                ex.getMessage(),
                null
        );
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(400,
                "Bad Request",
                ex.getMessage() != null ? ex.getMessage() : "Invalid argument provided",
                List.of("Please check the provided arguments and try again."));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleJsonParseException(HttpMessageNotReadableException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                400,
                "Bad Request",
                "Malformed JSON request",
                List.of(ex.getMessage() != null ? ex.getMessage() : "The request body is not readable or is malformed. Please check the JSON format.")
        );
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
