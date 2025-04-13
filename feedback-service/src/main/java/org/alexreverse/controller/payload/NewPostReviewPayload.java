package org.alexreverse.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewPostReviewPayload(
        @NotNull(message = "{feedback.posts.reviews.create.errors.post_id_is_null}")
        Long postId,
        @Size(max = 1000, message = "{feedback.posts.reviews.create.errors.review_is_too_big}")
        String review,
        @NotNull
        String userName) {
}
