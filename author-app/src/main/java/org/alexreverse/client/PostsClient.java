package org.alexreverse.client;

import org.alexreverse.entity.Post;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostsClient {

    Flux<Post> findAllPosts(String filter);

    Mono<Post> findPost(int Id);

    Mono<Post> createPost(String title, String description);

    Mono<Void> updatePost(int postId, String title, String description);

    Mono<Void> deletePost(int postId);
}
