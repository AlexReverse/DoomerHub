package org.alexreverse.service;

import org.alexreverse.entity.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {
    Flux<Post> findAllPosts(String filter);

    Mono<Post> createPost(String title, String description);

    Mono<Post> findPost(int postId);

    Mono<Void> updatePost(int id, String title, String description);

    Mono<Void> deletePost(Integer id);
}
