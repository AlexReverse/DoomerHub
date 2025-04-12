package org.alexreverse.repository;

import org.alexreverse.entity.PostReview;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface PostReviewRepository extends ReactiveCrudRepository<PostReview, Long> {

    @Query("{'postId': ?0}")
    Flux<PostReview> findAllByPostId(Long postId);
}
