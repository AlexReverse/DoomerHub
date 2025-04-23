package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.PostReview;
import org.alexreverse.repository.PostReviewRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DefaultPostReviewsService implements PostReviewsService {

    private final PostReviewRepository postReviewRepository;
    @Override
    public Mono<PostReview> createPostReview(Long postId, String review, String userName) {
        return this.postReviewRepository.save(new PostReview(null, postId, review, userName, LocalDateTime.now()));
    }

    @Override
    public Flux<PostReview> findPostReviewsByPostId(Long postId) {
        return this.postReviewRepository.findAllByPostId(postId);
    }

    @Override
    public Mono<Void> deletePostReview(Long reviewId, String userName) {
        return this.postReviewRepository.deleteByIdAndUserName(reviewId, userName);
    }
}
