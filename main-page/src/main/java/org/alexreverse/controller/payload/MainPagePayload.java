package org.alexreverse.controller.payload;

import java.time.LocalDate;
import java.util.UUID;

public record MainPagePayload(UUID userId, String nickname, String name, String surName, String city, LocalDate birthDay,
                                 String description) {
}
