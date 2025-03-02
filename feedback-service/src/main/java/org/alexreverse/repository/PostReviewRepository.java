package org.alexreverse.repository;

import org.alexreverse.entity.PostReview;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface PostReviewRepository extends ReactiveCrudRepository<PostReview, UUID> {

    @Query("{'postId': ?0}")
    Flux<PostReview> findAllByPostId(int postId);
}
