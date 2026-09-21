package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.NewFavouritePostPayload;
import org.alexreverse.dto.FavouritePostDto;
import org.alexreverse.dto.mapper.FeedbackMapper;
import org.alexreverse.repository.FavouritePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DefaultFavouritePostsService implements FavouritePostsService {

    private final FavouritePostRepository favouriteRepository;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public Mono<FavouritePostDto> createFavouritePost(NewFavouritePostPayload payload) {
        return favouriteRepository
                .save(feedbackMapper.createEntity(payload, LocalDateTime.now()))
                .map(feedbackMapper::entityToDto);
    }

    @Override
    public Mono<Void> removePostFromFavourites(Long postId, String userName) {
        return favouriteRepository.deleteByPostIdAndUserName(postId, userName);
    }

    @Override
    public Mono<FavouritePostDto> findFavouritePostByPost(Long postId, String userName) {
        return favouriteRepository
                .findByPostIdAndUserName(postId, userName)
                .map(feedbackMapper::entityToDto);
    }

    @Override
    public Flux<FavouritePostDto> findFavouritePosts(String userName) {
        return favouriteRepository
                .findAllByUserName(userName)
                .map(feedbackMapper::entityToDto);
    }

    @Override
    public Mono<Void> deleteFavouritesFromPost(Long postId) {
        return favouriteRepository.deleteByPostId(postId);
    }
}
