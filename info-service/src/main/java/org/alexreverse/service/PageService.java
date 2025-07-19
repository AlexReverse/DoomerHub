package org.alexreverse.service;

import org.alexreverse.entity.MainPage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PageService {
    Mono<MainPage> findMainPage(UUID userId);

    Flux<MainPage> findAllMainPageByNameOrSurNameOrNickname(String filter);

    Mono<MainPage> createMainPage(UUID userId, String nickname, String name, String surName, String city, Byte age, String description);

    Mono<Void> updateMainPage(UUID userId, String nickname, String name, String surName, String city, Byte age, String description);

    Mono<Void> deleteMainPage(UUID userId);
}
