package org.alexreverse.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AuthorInformationDto(
        UUID userId,
        String nickname,
        String name,
        String surName,
        String city,
        LocalDate birthDay,
        String description,
        LocalDateTime registrationDate) {}
