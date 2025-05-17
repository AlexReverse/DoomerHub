package org.alexreverse.repository;

import org.alexreverse.entity.PostTranslation;
import org.springframework.data.repository.CrudRepository;

public interface PostTranslationRepository extends CrudRepository<PostTranslation, Long> {
    PostTranslation findByPostId(Long postId);

    Void deleteByPostId(Long postId);
}
