package org.alexreverse.client;

import org.alexreverse.entity.PostReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostReviewsClient {

    Flux<PostReview> findPostReviewsByPostId(Long postId);

    Mono<PostReview> createPostReview(Long postId, Long rating, String review);
}
