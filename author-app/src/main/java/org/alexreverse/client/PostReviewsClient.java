package org.alexreverse.client;

import org.alexreverse.entity.PostReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostReviewsClient {

    Flux<PostReview> findPostReviewsByPostId(Integer postId);

    Mono<PostReview> createPostReview(Integer postId, Integer rating, String review);
}
