package org.alexreverse.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.alexreverse.entity.Education.FORM_EDUCATION;

public record EducationPayload(
        @NotNull
        @Size(min = 3, max = 50)
        String heiName,
        @NotNull
        @Size(max = 4)
        String educationStartDate,
        @NotNull
        @Size(max = 4)
        String educationEndDate,
        @NotNull
        @Size(min = 3, max = 50)
        String specialization,
        @NotNull
        FORM_EDUCATION formEducation) {}
