package org.alexreverse.service;

import org.alexreverse.entity.PostsTranslation;
import reactor.core.publisher.Mono;

public interface PostsTranslationService {

    Mono<PostsTranslation> findPostTranslation(Long id);

    Mono<PostsTranslation> createPostTranslation(String translatedDescription);

    Mono<Void> deletePostTranslation(Long id);
}
