package org.alexreverse.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public record MainPage(UUID userId, String nickname, String name, String surName, String city, Byte birthDay,
                       String description, LocalDateTime registrationDate) {
}
