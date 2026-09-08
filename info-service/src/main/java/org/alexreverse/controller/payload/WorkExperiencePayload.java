package org.alexreverse.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record WorkExperiencePayload(
        @NotNull
        @Size(min = 3, max = 50)
        String companyName,
        @NotNull
        LocalDate workStartDate,
        @NotNull
        LocalDate workEndDate,
        @NotNull
        @Size(min = 3, max = 50)
        String companyPosition,
        @Size(max = 100)
        String responsibilities) {}
