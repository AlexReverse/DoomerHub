package org.alexreverse.service;

import org.alexreverse.entity.Post;
import reactor.core.publisher.Mono;

public interface PostService {

    Mono<Post> findPost(Long id);

    Mono<Void> updatePost(Long id, String title, String description);

    Mono<Void> deletePost(Long id);
}
