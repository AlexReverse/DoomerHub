package org.alexreverse.client.payload;

import java.time.LocalDate;

public record MainPagePayload(String nickname, String name, String surName, String city, LocalDate birthDay,
                              String description) {
}
