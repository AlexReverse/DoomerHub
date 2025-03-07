package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.FavouritePost;
import org.alexreverse.repository.FavouritePostRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultFavouritePostsService implements FavouritePostsService {

    private final FavouritePostRepository favouriteRepository;

    @Override
    public Mono<FavouritePost> addPostToFavourites(int postId) {
        return this.favouriteRepository.save(new FavouritePost(UUID.randomUUID(), postId));
    }

    @Override
    public Mono<Void> removePostFromFavourites(int postId) {
        return this.favouriteRepository.deleteByPostId(postId);
    }

    @Override
    public Mono<FavouritePost> findFavouritePostByPost(int postId) {
        return this.favouriteRepository.findByPostId(postId);
    }

    @Override
    public Flux<FavouritePost> findFavouritePosts() {
        return this.favouriteRepository.findAll();
    }
}
