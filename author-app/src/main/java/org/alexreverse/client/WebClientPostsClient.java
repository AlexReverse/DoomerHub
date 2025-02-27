package org.alexreverse.client;

import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.Post;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Post> findPost(Integer id) {
        return this.webClient.get()
                .uri("/search-api/posts/{postId}", id)
                .retrieve()
                .bodyToMono(Post.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }
}
