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
    public Flux<FavouritePost> findFavouritePosts(String userName) {
        return this.webClient.get()
                .uri("favourite/{userName}", userName)
                .retrieve()
                .bodyToFlux(FavouritePost.class);
    }

    @Override
    public Mono<FavouritePost> findFavouritePostByPostIdAndUser(Long postId, String userName) {
        return this.webClient.get()
                .uri("favourite/{userName}/by-post-id/{postId}", userName, postId)
                .retrieve()
                .bodyToMono(FavouritePost.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    @Override
    public Mono<FavouritePost> addPostToFavourites(Long postId, String userName) {
        return this.webClient.post()
                .uri("favourite/{userName}", userName)
                .bodyValue(new NewFavouritePostPayload(postId, userName))
                .retrieve()
                .bodyToMono(FavouritePost.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors"))));
    }

    @Override
    public Mono<Void> removePostFromFavourites(Long postId, String userName) {
        return this.webClient
                .delete()
                .uri("favourite/{userName}/by-post-id/{postId}", userName, postId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }

    @Override
    public Mono<Void> deleteFavouritesFromPost(Long postId) {
        return this.webClient
                .delete()
                .uri("favourite/by-post-id/{postId:\\d+}", postId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
