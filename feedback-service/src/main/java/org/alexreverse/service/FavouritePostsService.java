package org.alexreverse.service;

import org.alexreverse.entity.FavouritePost;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouritePostsService {

    Mono<FavouritePost> addPostToFavourites(Integer postId);

    Mono<Void> removePostFromFavourites(Integer postId);

    Mono<FavouritePost> findFavouritePostByPost(Integer id);

    Flux<FavouritePost> findFavouritePosts();
}
