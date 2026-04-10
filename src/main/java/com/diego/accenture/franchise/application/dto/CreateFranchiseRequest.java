package com.diego.accenture.franchise.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateFranchiseRequest (
    @NotBlank(message = "Franchise name is required")
    String name
) {
}
