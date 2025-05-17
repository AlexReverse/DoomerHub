package org.alexreverse.service;

import org.alexreverse.entity.PostTranslation;

public interface PostTranslationService {

    PostTranslation findPostTranslation(Long postId);

    PostTranslation createPostTranslation(Long postId, String translatedDescription);

    Void deletePostTranslation(Long postId);
}
