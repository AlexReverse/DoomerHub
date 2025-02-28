package org.alexreverse.repository;

import org.alexreverse.entity.PostReview;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class InMemoryPostReviewRepository implements PostReviewRepository {

    private final List<PostReview> postReviews = Collections.synchronizedList(new ArrayList<>());
    @Override
    public Mono<PostReview> save(PostReview postReview) {
        this.postReviews.add(postReview);
        return Mono.just(postReview);
    }

    @Override
    public Flux<PostReview> findAllByPostId(Integer postId) {
        return Flux.fromIterable(this.postReviews)
                .filter(postReview -> postReview.getPostId().equals(postId));
    }
}
