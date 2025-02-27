package org.alexreverse.client;

import org.alexreverse.entity.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostsClient {

    Flux<Post> findAllPosts(String filter);

    Mono<Post> findPost(Integer Id);
}
