package org.alexreverse.repository;

import org.alexreverse.entity.FavouritePost;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FavouritePostRepository extends ReactiveCrudRepository<FavouritePost, UUID> {


    Mono<Void> deleteByPostId(int postId);

    Mono<FavouritePost> findByPostIdAndUser(int postId, String user);

    Flux<FavouritePost> findAllByUser(String user);
}
