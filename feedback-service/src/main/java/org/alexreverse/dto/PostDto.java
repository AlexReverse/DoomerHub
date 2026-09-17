package org.alexreverse.dto;

import java.time.LocalDateTime;

public record PostDto(Long id, String title, String description, LocalDateTime postDate) {}
