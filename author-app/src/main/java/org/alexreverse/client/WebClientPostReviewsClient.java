package org.alexreverse.client;

import lombok.RequiredArgsConstructor;
import org.alexreverse.client.exception.ClientBadRequestException;
import org.alexreverse.client.payload.NewPostReviewPayload;
import org.alexreverse.entity.PostReview;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class WebClientPostReviewsClient implements PostReviewsClient {

    private final WebClient webClient;
    @Override
    public Flux<PostReview> findPostReviewsByPostId(Long postId) {
        return this.webClient
                .get()
                .uri("/feedback-api/post-reviews/by-post-id/{postId}", postId)
                .retrieve()
                .bodyToFlux(PostReview.class);
    }

    @Override
    public Mono<PostReview> createPostReview(Long postId, String review, String userName) {
        return this.webClient
                .post()
                .uri("/feedback-api/post-reviews")
                .bodyValue(new NewPostReviewPayload(postId, review, userName))
                .retrieve()
                .bodyToMono(PostReview.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                        ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                .getProperties().get("errors"))));
    }
}
