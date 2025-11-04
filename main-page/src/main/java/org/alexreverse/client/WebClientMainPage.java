package org.alexreverse.client;

import lombok.RequiredArgsConstructor;
import org.alexreverse.client.exception.ClientBadRequestException;
import org.alexreverse.client.payload.MainPagePayload;
import org.alexreverse.entity.MainPage;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class WebClientMainPage implements MainPageClient {

    private final WebClient webClient;

    @Override
    public Flux<MainPage> findAllMainPage(String filter) {
        return this.webClient.get()
                .uri("/main-page", filter)
                .retrieve()
                .bodyToFlux(MainPage.class);
    }

    @Override
    public Mono<MainPage> findMainPage() {
        return this.webClient.get()
                .uri("/main-page")
                .retrieve()
                .bodyToMono(MainPage.class);
    }

    @Override
    public Mono<MainPage> createMainPage(String nickname, String name, String surName, String city, LocalDate birthDay, String description) {
        return this.webClient
                .post()
                .uri("/main-page")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new MainPagePayload(nickname, name, surName, city, birthDay, description))
                .retrieve()
                .bodyToMono(MainPage.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors"))));
    }

    @Override
    public Mono<Void> updateMainPage(String nickname, String name, String surName, String city, LocalDate birthDay, String description) {
        return this.webClient
                .patch()
                .uri("/main-page")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new MainPagePayload(nickname, name, surName, city, birthDay, description))
                .retrieve()
                .toBodilessEntity()
                .then()
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors"))));
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
