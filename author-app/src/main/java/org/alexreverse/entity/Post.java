package org.alexreverse.entity;

import java.util.UUID;

public record Post(Long id, String title, String description, UUID userId) {
}
