package com.diego.accenture.franchise.application.dto;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        int status,
        String error,
        String message,
        Instant timestamp,
        List<String> details
) {
}
