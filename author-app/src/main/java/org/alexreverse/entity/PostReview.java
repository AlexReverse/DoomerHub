package org.alexreverse.entity;

public record PostReview(Long id, Long postId, String review, String userName) {
}
