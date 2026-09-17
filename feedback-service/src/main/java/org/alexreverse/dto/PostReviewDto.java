package org.alexreverse.dto;

import java.time.LocalDateTime;

public record PostReviewDto(Long id, Long postId, String review, String userName, LocalDateTime postReviewDate) {
}
