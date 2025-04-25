package org.alexreverse.service;

import org.alexreverse.entity.PostReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostReviewsService {

    Mono<PostReview> createPostReview(Long postId, String review, String userName);

    Flux<PostReview> findPostReviewsByPostId(Long postId);

    Mono<Void> deletePostReview(Long reviewId, String userName);

    Mono<Void> deletePostReviewByPostId(Long reviewId);
}
