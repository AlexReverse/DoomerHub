package org.alexreverse.controller.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewPostReviewPayload(
        @NotNull(message = "{feedback.posts.reviews.create.errors.post_id_is_null}")
        Integer postId,
        @NotNull(message = "{feedback.posts.reviews.create.errors.rating_is_null}")
        @Min(value = 1, message = "{feedback.posts.reviews.create.errors.rating_is_below_min}")
        @Max(value = 5, message = "{feedback.posts.reviews.create.errors.rating_is_above_max}")
        Integer rating,
        @Size(max = 1000, message = "{feedback.posts.reviews.create.errors.review_is_too_big}")
        String review) {
}
