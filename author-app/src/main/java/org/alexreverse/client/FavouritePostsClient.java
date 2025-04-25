package org.alexreverse.client;

import org.alexreverse.entity.FavouritePost;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouritePostsClient {

    Flux<FavouritePost> findFavouritePosts(String user);

    Mono<FavouritePost> findFavouritePostByPostIdAndUser(Long postId, String user);

    Mono<FavouritePost> addPostToFavourites(Long postId, String userId);

    Mono<Void> removePostFromFavourites(Long postId, String userId);

    Mono<Void> deleteFavouritesFromPost(Long postId);
}
