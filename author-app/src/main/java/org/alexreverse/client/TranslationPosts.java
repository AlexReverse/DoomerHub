package org.alexreverse.client;
import reactor.core.publisher.Mono;

public interface TranslationPosts {
    Mono<String> createTranslation(String englishTranslation);
}
