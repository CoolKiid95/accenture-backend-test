package com.diego.accenture.franchise.application.dto;

import jakarta.validation.constraints.NotBlank;

public record AddBranchRequest(
        @NotBlank(message = "Branch name is required")
        String name
) {
}
