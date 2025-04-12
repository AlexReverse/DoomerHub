package org.alexreverse.repository;

import org.alexreverse.entity.FavouritePost;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface FavouritePostRepository extends ReactiveCrudRepository<FavouritePost, Long> {

    Mono<Void> deleteByPostIdAndUserName(Long postId, String userName);

    Mono<FavouritePost> findByPostIdAndUserName(Long postId, String userName);

    Flux<FavouritePost> findAllByUserName(String userName);
}
