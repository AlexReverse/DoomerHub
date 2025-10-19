package org.alexreverse.client;

import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.MainPage;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class WebClientMainPage implements MainPageClient {

    private final WebClient webClient;
    // Необходимо будет избавиться от передачи userId в конструкторе
    @Override
    public Flux<MainPage> findAllMainPage(String filter) {
        return this.webClient.get()
                .uri("/main-page", filter)
                .retrieve()
                .bodyToFlux(MainPage.class);
    }

    @Override
    public Mono<MainPage> findMainPage() {
        return null;
    }

    @Override
    public Mono<MainPage> createMainPage(String nickname, String name, String surName, String city, Byte age, String description) {
        return null;
    }

    @Override
    public Mono<Void> updateMainPage(String nickname, String name, String surName, String city, Byte age, String description) {
        return null;
    }

    @Override
    public Mono<Void> deleteMainPage() {
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
