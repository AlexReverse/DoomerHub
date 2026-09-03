package org.alexreverse.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record PostReview(Long id, Long postId, String review, String userName, LocalDateTime postReviewDate, String dateTime) {

    public String dateTime() {
        return postReviewDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }
}
