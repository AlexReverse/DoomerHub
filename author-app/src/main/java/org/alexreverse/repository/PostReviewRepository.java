package org.alexreverse.repository;

import org.alexreverse.entity.PostReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostReviewRepository {

    Mono<PostReview> save(PostReview postReview);

    Flux<PostReview> findAllByPostId(Integer postId);
}
