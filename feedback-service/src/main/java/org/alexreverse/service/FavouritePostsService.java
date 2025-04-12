package org.alexreverse.service;

import org.alexreverse.entity.FavouritePost;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouritePostsService {

    Mono<FavouritePost> createFavouritePost(Long postId, String userName);

    Mono<Void> removePostFromFavourites(Long postId, String userName);
    Mono<FavouritePost> findFavouritePostByPost(Long postId, String userName);
    Flux<FavouritePost> findFavouritePosts(String userName);
}
