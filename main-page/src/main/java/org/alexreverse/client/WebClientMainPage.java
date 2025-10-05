package org.alexreverse.client;

import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.MainPage;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
public class WebClientMainPage implements MainPageClient {

    private final WebClient webClient;
    // Необходимо будет избавиться от передачи userId в конструкторе
    @Override
    public Flux<MainPage> findAllMainPage(String filter) {
        return null;
    }

    @Override
    public Mono<MainPage> findMainPage(UUID userId) {
        return null;
    }

    @Override
    public Mono<MainPage> createMainPage(UUID userId, String nickname, String name, String surName, String city, Byte age, String description) {
        return null;
    }

    @Override
    public Mono<Void> updateMainPage(UUID userId, String nickname, String name, String surName, String city, Byte age, String description) {
        return null;
    }

    @Override
    public Mono<Void> deleteMainPage(UUID userId) {
        try {
            return this.webClient
                    .delete()
                    .uri("/main-page")
                    .retrieve()
                    .toBodilessEntity()
                    .then();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
