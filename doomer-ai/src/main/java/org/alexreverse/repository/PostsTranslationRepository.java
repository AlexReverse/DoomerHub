package org.alexreverse.repository;

import org.alexreverse.entity.PostsTranslation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostsTranslationRepository extends ReactiveCrudRepository<PostsTranslation, Long> {
}
