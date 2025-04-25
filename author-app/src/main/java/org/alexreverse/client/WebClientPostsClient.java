package org.alexreverse.client;

import lombok.RequiredArgsConstructor;
import org.alexreverse.client.exception.ClientBadRequestException;
import org.alexreverse.controller.payload.NewPostPayload;
import org.alexreverse.controller.payload.UpdatePostPayload;
import org.alexreverse.entity.Post;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class WebClientPostsClient implements PostsClient {

    private final WebClient webClient;

    @Override
    public Flux<Post> findAllPosts(String filter) {
        return this.webClient.get()
                .uri("/search-api/posts?filter={filter}", filter)
                .retrieve()
                .bodyToFlux(Post.class);
    }

    @Override
    public Mono<Post> findPost(Long id) {
        return this.webClient.get()
                .uri("/search-api/posts/{postId}", id)
                .retrieve()
                .bodyToMono(Post.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    @Override
    public Mono<Post> createPost(String title, String description, String userId, Boolean translate) {
        return this.webClient
                .post()
                .uri("/search-api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new NewPostPayload(title, description, userId, translate))
                .retrieve()
                .bodyToMono(Post.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors"))));
    }

    @Override
    public Mono<Void> updatePost(Long postId, String title, String description) {
        return this.webClient
                .patch()
                .uri("/search-api/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdatePostPayload(title, description))
                .retrieve()
                .toBodilessEntity().then()
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors"))));
    }

    @Override
    public Mono<Void> deletePost(Long postId) {
        try {
            return this.webClient
                    .delete()
                    .uri("/search-api/posts/{postId}", postId)
                    .retrieve()
                    .toBodilessEntity().then();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
