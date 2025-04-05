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
    public Flux<FavouritePost> findFavouritePosts(String user) {
        return this.webClient.get()
                .uri("favourite/{user}", user)
                .retrieve()
                .bodyToFlux(FavouritePost.class);
    }

    @Override
    public Mono<FavouritePost> findFavouritePostByPostId(int postId, String user) {
        return this.webClient.get()
                .uri("favourite/by-post-id/{postId}", postId, user)
                .retrieve()
                .bodyToMono(FavouritePost.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    @Override
    public Mono<FavouritePost> addPostToFavourites(int postId, String user) {
        return this.webClient.post()
                .uri("favourite")
                .bodyValue(new NewFavouritePostPayload(postId, user))
                .retrieve()
                .bodyToMono(FavouritePost.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors"))));
    }

    @Override
    public Mono<Void> removePostFromFavourites(int postId, String userId) {
        return this.webClient.delete().uri("favourite/by-post-id/{postId}", postId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
