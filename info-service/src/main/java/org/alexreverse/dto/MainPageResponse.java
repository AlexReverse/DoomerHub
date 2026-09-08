package org.alexreverse.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record MainPageResponse(
        UUID userId,
        String nickname,
        String name,
        String surName,
        String city,
        LocalDate birthDay,
        String description,
        LocalDateTime registrationDate,
        List<EducationDto> education,
        List<WorkExperienceDto> workExperiences) {}