package org.alexreverse.service;

import org.alexreverse.controller.payload.NewPostPayload;
import org.alexreverse.dto.PostDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PostsService {

    Flux<PostDto> findAllPosts(String filter, Pageable pageable);

    Mono<PostDto> createPost(NewPostPayload payload, UUID userId);

    Flux<PostDto> findAllPostsByUser(UUID userId, Pageable pageable);
}
