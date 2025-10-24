package org.alexreverse.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MainPagePayload(
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
        LocalDate birthDay,
        @Size(max = 100)
        String description) {}
