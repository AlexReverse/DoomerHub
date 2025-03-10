package org.alexreverse.client;

import org.alexreverse.entity.Post;
import org.apache.coyote.BadRequestException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostsClient {

    Flux<Post> findAllPosts(String filter);

    Mono<Post> findPost(int Id);

    Mono<Post> createPost(String title, String description) throws BadRequestException;

    Mono<Void> updatePost(int postId, String title, String description) throws BadRequestException;

    Mono<Void> deletePost(int postId);
}
