package org.alexreverse.service;

import org.alexreverse.controller.payload.NewPostReviewPayload;
import org.alexreverse.dto.PostReviewDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostReviewsService {

    Mono<PostReviewDto> createPostReview(NewPostReviewPayload payload);

    Flux<PostReviewDto> findPostReviewsByPostId(Long postId);

    Mono<Void> deletePostReview(Long reviewId, String userName);

    Mono<Void> deletePostReviewByPostId(Long reviewId);
}
