package com.diego.accenture.franchise.infrastructure.exception;

import com.diego.accenture.franchise.application.dto.ErrorResponse;
import com.diego.accenture.franchise.domain.exception.BranchNotFoundException;
import com.diego.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.diego.accenture.franchise.domain.exception.InvalidStockException;
import com.diego.accenture.franchise.domain.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FranchiseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFranchiseNotFound(FranchiseNotFoundException ex){
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), List.of());
    }

    @ExceptionHandler(BranchNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBranchNotFound(BranchNotFoundException ex){
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), List.of());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), List.of());
    }

    @ExceptionHandler(InvalidStockException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStock(InvalidStockException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), List.of());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(WebExchangeBindException ex){
        List<String> details = ex.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                details
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                List.of(ex.getMessage())
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String message,
            List<String> details
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                Instant.now(),
                details
        );

        return ResponseEntity.status(status).body(errorResponse);
    }
}
