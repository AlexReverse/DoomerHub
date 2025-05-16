package org.alexreverse.repository;

import org.alexreverse.entity.PostTranslation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PostTranslationRepository extends ReactiveCrudRepository<PostTranslation, Long> {
    Mono<PostTranslation> findByPostId(Long postId);

    Mono<Void> deleteByPostId(Long postId);
}
