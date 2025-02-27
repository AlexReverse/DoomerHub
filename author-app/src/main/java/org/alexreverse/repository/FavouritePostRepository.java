package org.alexreverse.repository;

import org.alexreverse.entity.FavouritePost;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouritePostRepository {

    Mono<FavouritePost> save(FavouritePost favouritePost);

    Mono<Void> deleteByPostId(Integer postId);

    Mono<FavouritePost> findByPostId(Integer postId);

    Flux<FavouritePost> findAll();
}
