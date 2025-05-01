package org.alexreverse.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebClientTranslationPosts implements TranslationPosts{

    private final WebClient webClient;
    @Override
    public Mono<String> createTranslation(String description) {
        return this.webClient
                .post()
                .uri("/translate/to-english")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(description)
                .retrieve()
                .bodyToMono(String.class);
    }
}
