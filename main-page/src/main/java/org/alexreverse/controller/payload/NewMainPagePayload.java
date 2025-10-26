package org.alexreverse.controller.payload;

import java.time.LocalDate;

public record NewMainPagePayload(String nickname, String name, String surName, String city, LocalDate birthDay,
                                 String description) {
}
