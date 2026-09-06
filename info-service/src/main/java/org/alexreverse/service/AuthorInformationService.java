package org.alexreverse.service;

import org.alexreverse.entity.AuthorInformation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface AuthorInformationService {

    Mono<AuthorInformation> findAuthorInformation(UUID userId);

    Flux<AuthorInformation> findAllAuthorInformationByNameOrSurNameOrNickname(String filter);

    Mono<AuthorInformation> createAuthorInformation(UUID userId, String nickname, String name, String surName, String city, LocalDate birthDay, String description);

    Mono<Void> updateAuthorInformation(UUID userId, String nickname, String name, String surName, String city, LocalDate birthDay, String description);

    Mono<Void> deleteAuthorInformation(UUID userId);
}
