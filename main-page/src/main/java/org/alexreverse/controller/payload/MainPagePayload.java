package org.alexreverse.controller.payload;

import java.util.UUID;

public record MainPagePayload(UUID userId, String nickname, String name, String surName, String city, Byte age,
                                 String description) {
}
