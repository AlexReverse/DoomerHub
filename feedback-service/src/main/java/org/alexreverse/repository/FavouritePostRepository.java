package org.alexreverse.repository;

import org.alexreverse.entity.FavouritePost;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FavouritePostRepository extends ReactiveCrudRepository<FavouritePost, UUID> {


    Mono<Void> deleteByPostId(int postId);

    Mono<FavouritePost> findByPostId(int postId);

}
