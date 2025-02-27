package org.alexreverse.service;

import org.alexreverse.entity.PostReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostReviewsService {

    Mono<PostReview> createPostReview(Integer postId, Integer rating, String review);

    Flux<PostReview> findPostReviewsByPost(Integer postId);
}
