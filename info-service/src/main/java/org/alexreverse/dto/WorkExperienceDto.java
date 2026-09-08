package org.alexreverse.dto;

import java.time.LocalDate;

public record WorkExperienceDto(
        Integer id,
        String companyName,
        LocalDate workStartDate,
        LocalDate workEndDate,
        String companyPosition,
        String responsibilities) {
}
