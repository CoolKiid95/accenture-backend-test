package com.diego.accenture.franchise.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddProductRequest(
        @NotBlank(message = "Product name is required")
        String name,

        @NotNull(message = "Stock is required")
        @Min(value = 0, message = "Stock must be greater than or equal to 0")
        Integer stock
) {
}
