package com.example.demo.domain.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record StartTestRequest(
        @NotNull UUID testId,
        @NotNull UUID studentId
) {
}


