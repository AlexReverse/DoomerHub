package org.alexreverse.dto;

public record EducationDto(
        Integer id,
        String heiName,
        String educationStartDate,
        String educationEndDate,
        String specialization,
        String formEducation) {}
