package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.PostReview;
import org.alexreverse.repository.PostReviewRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultPostReviewsService implements PostReviewsService {

    private final PostReviewRepository postReviewRepository;
    @Override
    public Mono<PostReview> createPostReview(Long postId, Long rating, String review) {
        return this.postReviewRepository.save(
                new PostReview(null, postId, rating, review)
        );
    }

    @Override
    public Flux<PostReview> findPostReviewsByPost(Long postId) {
        return this.postReviewRepository.findAllByPostId(postId);
    }
}
