package com.example.demo.domain.response;

import com.example.demo.domain.model.TestState;

import java.util.UUID;

public record TestResponse(
        UUID id,
        String name,
        TestState state
) {
}
