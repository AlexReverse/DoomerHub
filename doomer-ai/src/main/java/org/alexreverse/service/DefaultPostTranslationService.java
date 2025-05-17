package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.PostTranslation;
import org.alexreverse.repository.PostTranslationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DefaultPostTranslationService implements PostTranslationService {

    private final PostTranslationRepository repository;

    @Override
    public PostTranslation findPostTranslation(Long postId) {
        return this.repository.findByPostId(postId);
    }

    @Override
    public PostTranslation createPostTranslation(Long postId, String translatedDescription) {
        return this.repository.save(new PostTranslation(null, postId, translatedDescription, LocalDateTime.now()));
    }

    @Override
    public Void deletePostTranslation(Long postId) {
        return this.repository.deleteByPostId(postId);
    }
}
