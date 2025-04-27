package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.PostsTranslation;
import org.alexreverse.repository.PostsTranslationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DefaultPostsTranslationService implements PostsTranslationService {

    private final PostsTranslationRepository repository;

    @Override
    public Mono<PostsTranslation> findPostTranslation(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Mono<PostsTranslation> createPostTranslation(String translatedDescription) {
        return this.repository.save(new PostsTranslation(null, translatedDescription, LocalDateTime.now()));
    }

    @Override
    public Mono<Void> deletePostTranslation(Long id) {
        return this.repository.deleteById(id);
    }
}
