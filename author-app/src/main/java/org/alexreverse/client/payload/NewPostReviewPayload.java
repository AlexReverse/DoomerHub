package org.alexreverse.client.payload;

public record NewPostReviewPayload(Long postId, String review, String userName) {
}
