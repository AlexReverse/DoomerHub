package org.alexreverse.client;

import lombok.RequiredArgsConstructor;
import org.alexreverse.client.exception.ClientBadRequestException;
import org.alexreverse.client.payload.NewFavouritePostPayload;
import org.alexreverse.entity.FavouritePost;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class WebClientFavouritePostsClient implements FavouritePostsClient {

    private final WebClient webClient;
    @Override
    public Flux<FavouritePost> findFavouritePosts() {
        return this.webClient.get()
                .uri("/feedback-api/favourite-posts")
                .retrieve()
                .bodyToFlux(FavouritePost.class);
    }

    @Override
    public Mono<FavouritePost> findFavouritePostByPostId(int postId) {
        return this.webClient.get()
                .uri("/feedback-api/favourite-posts/by-post-id/{postId}", postId)
                .retrieve()
                .bodyToMono(FavouritePost.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    @Override
    public Mono<FavouritePost> addPostToFavourites(int postId) {
        return this.webClient.post()
                .uri("/feedback-api/favourite-posts")
                .bodyValue(new NewFavouritePostPayload(postId))
                .retrieve()
                .bodyToMono(FavouritePost.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors"))));
    }

    @Override
    public Mono<Void> removePostFromFavourites(int postId) {
        return this.webClient.delete().uri("/feedback-api/favourite-posts/by-post-id/{postId}", postId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
