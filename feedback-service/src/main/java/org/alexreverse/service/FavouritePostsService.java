package org.alexreverse.service;

import org.alexreverse.entity.FavouritePost;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouritePostsService {

    Mono<FavouritePost> createFavouritePost(int postId, String userName);

    Mono<Void> removePostFromFavourites(int postId, String userName);
    Mono<FavouritePost> findFavouritePostByPost(int postId, String userName);
    Flux<FavouritePost> findFavouritePosts(String userName);
}
