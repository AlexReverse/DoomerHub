package org.alexreverse.service;

import org.alexreverse.controller.payload.UpdatePostPayload;
import org.alexreverse.dto.PostDto;
import reactor.core.publisher.Mono;

public interface PostService {

    Mono<PostDto> findPost(Long id);

    Mono<Void> updatePost(Long id, UpdatePostPayload payload);

    Mono<Void> deletePost(Long id);
}
