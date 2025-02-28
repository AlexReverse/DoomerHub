package org.alexreverse.entity;

import java.util.UUID;

public record PostReview(UUID id, int postId, int rating, String review) {
}
