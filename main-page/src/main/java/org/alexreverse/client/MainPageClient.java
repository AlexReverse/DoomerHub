package org.alexreverse.client;

import org.alexreverse.entity.MainPage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MainPageClient {

    Flux<MainPage> findAllMainPage(String filter);

    Mono<MainPage> findMainPage(UUID userId);

    Mono<MainPage> createMainPage(UUID userId, String nickname, String name, String surName, String city, Byte age,
                                  String description);

    Mono<Void> updateMainPage(UUID userId, String nickname, String name, String surName, String city, Byte age,
                              String description);

    Mono<Void> deleteMainPage(UUID userId);
}
