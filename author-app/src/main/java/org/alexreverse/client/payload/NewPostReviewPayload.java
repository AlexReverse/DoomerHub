package org.alexreverse.client.payload;

public record NewPostReviewPayload(Long id, Long rating, String review) {
}
