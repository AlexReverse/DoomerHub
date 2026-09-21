package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.NewPostReviewPayload;
import org.alexreverse.dto.PostReviewDto;
import org.alexreverse.dto.mapper.FeedbackMapper;
import org.alexreverse.repository.PostReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DefaultPostReviewsService implements PostReviewsService {

    private final PostReviewRepository postReviewRepository;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public Mono<PostReviewDto> createPostReview(NewPostReviewPayload payload) {
        return postReviewRepository
                .save(feedbackMapper.createEntity(payload, LocalDateTime.now()))
                .map(feedbackMapper::entityToDto);
    }

    @Override
    public Flux<PostReviewDto> findPostReviewsByPostId(Long postId) {
        return postReviewRepository
                .findAllByPostId(postId)
                .map(feedbackMapper::entityToDto);
    }

    @Override
    public Mono<Void> deletePostReview(Long reviewId, String userName) {
        return postReviewRepository.deleteByIdAndUserName(reviewId, userName);
    }

    @Override
    public Mono<Void> deletePostReviewByPostId(Long reviewId) {
        return postReviewRepository.deleteByPostId(reviewId);
    }
}
