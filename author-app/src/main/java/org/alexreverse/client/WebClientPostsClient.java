package org.alexreverse.client;

import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.NewPostPayload;
import org.alexreverse.controller.payload.UpdatePostPayload;
import org.alexreverse.entity.Post;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Post> findPost(int id) {
        return this.webClient.get()
                .uri("/search-api/posts/{postId}", id)
                .retrieve()
                .bodyToMono(Post.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    @Override
    public Mono<Post> createPost(String title, String description) throws BadRequestException {
        try {
            return this.webClient
                    .post()
                    .uri("/search-api/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new NewPostPayload(title, description))
                    .retrieve()
                    .bodyToMono(Post.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException(String.valueOf(problemDetail.getProperties().get("errors")));
        }
    }

    @Override
    public Mono<Void> updatePost(int postId, String title, String description) throws BadRequestException {
        try {
            return this.webClient
                    .patch()
                    .uri("/search-api/posts/{postId}", postId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new UpdatePostPayload(title, description))
                    .retrieve()
                    .toBodilessEntity().then();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException(String.valueOf(problemDetail.getProperties().get("errors")));
        }
    }

    @Override
    public Mono<Void> deletePost(int postId) {
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
