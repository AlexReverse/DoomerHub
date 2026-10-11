package org.alexreverse.repository;

import org.alexreverse.entity.PostReview;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EnableR2dbcRepositories
public interface PostReviewRepository extends ReactiveCrudRepository<PostReview, Long> {

    Flux<PostReview> findAllByPostId(Long postId);

    Mono<Void> deleteByIdAndUserName(Long reviewId, String userName);

    Mono<Void> deleteByPostId(Long postId);
}
