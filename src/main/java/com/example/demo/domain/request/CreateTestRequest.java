package com.example.demo.domain.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTestRequest(
        @NotNull
        @Size(min = 5, max = 100)
        String name
) {
}
