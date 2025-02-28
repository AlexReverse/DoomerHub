package org.alexreverse.client;

import io.micrometer.observation.ObservationFilter;
import org.alexreverse.entity.FavouritePost;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouritePostsClient {

    Flux<FavouritePost> findFavouritePosts();

    Mono<FavouritePost> findFavouritePostByPostId(int postId);

    Mono<FavouritePost> addPostToFavourites(int postId);

    Mono<Void> removePostFromFavourites(int postId);
}
