package org.alexreverse.service;

import org.alexreverse.entity.PostTranslation;
import reactor.core.publisher.Mono;

public interface PostTranslationService {

    Mono<PostTranslation> findPostTranslation(Long postId);

    Mono<PostTranslation> createPostTranslation(Long postId, String translatedDescription);

    Mono<Void> deletePostTranslation(Long postId);
}
