package org.alexreverse.repository;

import org.alexreverse.entity.PostReview;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface PostReviewRepository extends ReactiveCrudRepository<PostReview, Long> {

    Flux<PostReview> findAllByPostId(Long postId);
}
