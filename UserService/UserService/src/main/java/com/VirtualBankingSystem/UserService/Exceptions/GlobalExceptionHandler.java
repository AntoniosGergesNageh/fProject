package com.VirtualBankingSystem.UserService.Exceptions;

import com.VirtualBankingSystem.UserService.DTO.Response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                409,
                "Conflict",
                ex.getMessage(),
                List.of("A user with the same username or email already exists. Please choose a different username or email.")
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidUsernameOrPassword.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidUsernameOrPassword(InvalidUsernameOrPassword ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                401,
                "Unauthorized",
                ex.getMessage(),
                List.of("The username or password provided is incorrect. Please try again.")
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFound(UserNotFound ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                404,
                "Not Found",
                ex.getMessage(),
                List.of("The requested user could not be found.")
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                500,
                "Internal Server Error",
                "An unexpected error occurred.",
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
