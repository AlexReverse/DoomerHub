package org.alexreverse.client;

import org.alexreverse.entity.FavouritePost;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouritePostsClient {

    Flux<FavouritePost> findFavouritePosts(String user);

    Mono<FavouritePost> findFavouritePostByPostIdAndUser(int postId, String user);

    Mono<FavouritePost> addPostToFavourites(int postId, String userId);

    Mono<Void> removePostFromFavourites(int postId, String userId);
}
