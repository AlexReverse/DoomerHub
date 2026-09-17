package org.alexreverse.dto;

import java.time.LocalDateTime;

public record FavouritePostDto(Long id, Long postId, String userName, LocalDateTime favouritePostDate) {
}
