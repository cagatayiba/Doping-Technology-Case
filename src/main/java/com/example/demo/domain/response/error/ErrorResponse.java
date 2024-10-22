package com.example.demo.domain.response.error;

import lombok.Builder;
import java.time.Instant;

@Builder
public record ErrorResponse(
        Instant timestamp,
        String message,
        String description,
        int httpCode
) {
}
