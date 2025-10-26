package org.alexreverse.client;

import org.alexreverse.entity.MainPage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface MainPageClient {

    Flux<MainPage> findAllMainPage(String filter);

    Mono<MainPage> findMainPage();

    Mono<MainPage> createMainPage(String nickname, String name, String surName, String city, LocalDate birthDay,
                                  String description);

    Mono<Void> updateMainPage(String nickname, String name, String surName, String city, LocalDate birthDay,
                              String description);

    Mono<Void> deleteMainPage();
}
