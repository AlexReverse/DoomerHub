package org.alexreverse.client;

import org.alexreverse.entity.FavouritePost;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouritePostsClient {

    Flux<FavouritePost> findFavouritePosts(String user);

    Mono<FavouritePost> findFavouritePostByPostId(int postId, String userId);

    Mono<FavouritePost> addPostToFavourites(int postId, String userId);

    Mono<Void> removePostFromFavourites(int postId, String userId);
}
