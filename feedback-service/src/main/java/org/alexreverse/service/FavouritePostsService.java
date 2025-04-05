package org.alexreverse.service;

import org.alexreverse.entity.FavouritePost;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouritePostsService {

    Mono<FavouritePost> addPostToFavourites(int postId, String userId);

    Mono<Void> removePostFromFavourites(int postId);

    Mono<FavouritePost> findFavouritePostByPost(int id, String userId);

    Flux<FavouritePost> findFavouritePosts(String userId);
}
