package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.FavouritePost;
import org.alexreverse.repository.FavouritePostRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DefaultFavouritePostsService implements FavouritePostsService {

    private final FavouritePostRepository favouriteRepository;

    @Override
    public Mono<FavouritePost> createFavouritePost(Long postId, String userName) {
        return this.favouriteRepository.save(new FavouritePost(null, postId, userName, LocalDateTime.now()));
    }

    @Override
    public Mono<Void> removePostFromFavourites(Long postId, String userName) {
        return this.favouriteRepository.deleteByPostIdAndUserName(postId, userName);
    }

    @Override
    public Mono<FavouritePost> findFavouritePostByPost(Long postId, String userName) {
        return this.favouriteRepository.findByPostIdAndUserName(postId, userName);
    }

    @Override
    public Flux<FavouritePost> findFavouritePosts(String userName) {
        return this.favouriteRepository.findAllByUserName(userName);
    }
}
