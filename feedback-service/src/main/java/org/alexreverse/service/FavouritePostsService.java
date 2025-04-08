package org.alexreverse.service;

import org.alexreverse.entity.FavouritePost;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouritePostsService {

    Mono<FavouritePost> addPostToFavourites(int postId, String user);

    Mono<Void> removePostFromFavourites(int postId, String user);
    Mono<FavouritePost> findFavouritePostByPost(int postId, String userId);
    Flux<FavouritePost> findFavouritePosts(String userId);
}
