package org.alexreverse.repository;

import org.alexreverse.entity.FavouritePost;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EnableR2dbcRepositories
public interface FavouritePostRepository extends ReactiveCrudRepository<FavouritePost, Long> {

    Mono<Void> deleteByPostIdAndUserName(Long postId, String userName);

    Mono<FavouritePost> findByPostIdAndUserName(Long postId, String userName);

    Flux<FavouritePost> findAllByUserName(String userName);

    Mono<Void> deleteByPostId(Long postId);
}
