package com.diego.accenture.franchise.application.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateNameRequest(
        @NotBlank(message = "Name is required")
        String name
) {
}
