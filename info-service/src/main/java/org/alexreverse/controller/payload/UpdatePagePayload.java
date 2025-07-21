package org.alexreverse.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePagePayload(
        @NotNull
        @Size(min = 3, max = 50)
        String nickname,
        @NotNull
        @Size(min = 3, max = 50)
        String name,
        @NotNull
        @Size(min = 3, max = 50)
        String surName,
        @NotNull
        @Size(min = 3, max = 50)
        String city,
        @NotNull
        @Size(min = 14, max = 100)
        Byte age,
        @Size(max = 100)
        String description) {}
