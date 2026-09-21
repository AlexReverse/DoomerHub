package org.alexreverse.service;

import org.alexreverse.controller.payload.NewFavouritePostPayload;
import org.alexreverse.dto.FavouritePostDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavouritePostsService {

    Mono<FavouritePostDto> createFavouritePost(NewFavouritePostPayload payload);

    Mono<Void> removePostFromFavourites(Long postId, String userName);

    Mono<FavouritePostDto> findFavouritePostByPost(Long postId, String userName);

    Flux<FavouritePostDto> findFavouritePosts(String userName);

    Mono<Void> deleteFavouritesFromPost(Long postId);
}
