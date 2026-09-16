package org.alexreverse.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record WorkExperiencePatchPayload(
        @NotNull
        Long id,
        @NotNull
        @Size(min = 3, max = 50)
        String companyName,
        @NotNull
        LocalDate workStartDate,
        LocalDate workEndDate,
        @NotNull
        @Size(min = 3, max = 50)
        String companyPosition,
        @Size(max = 100)
        String responsibilities) {
}
