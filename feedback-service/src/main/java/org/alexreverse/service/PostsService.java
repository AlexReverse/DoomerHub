package org.alexreverse.service;

import org.alexreverse.entity.Post;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostsService {

    Flux<Post> findAllPosts(String filter, Pageable pageable);

    Mono<Post> createPost(String title, String description, String userId);

    Flux<Post> findAllPostsByUser(String userId, Pageable pageable);
}
