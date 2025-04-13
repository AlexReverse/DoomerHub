package org.alexreverse.controller.payload;

public record NewPostReviewPayload(Long rating, String review, String userName) {
}
