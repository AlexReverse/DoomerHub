package org.alexreverse.service;

import org.alexreverse.dto.AuthorInformationDto;
import org.alexreverse.dto.EducationDto;
import org.alexreverse.dto.MainPageResponse;
import org.alexreverse.dto.WorkExperienceDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface MainPageService {

    Mono<MainPageResponse> findMainPage(UUID uuid);

    Mono<AuthorInformationDto> findAuthorInformation(UUID userId);

    Flux<EducationDto> findEducation(UUID uuid);

    Flux<WorkExperienceDto> findWorkExperience(UUID uuid);

    //Flux<AuthorInformationDto> findAllAuthorInformationByNameOrSurNameOrNickname(String filter); todo

    Mono<AuthorInformationDto> createAuthorInformation(UUID userId, String nickname, String name, String surName, String city, LocalDate birthDay, String description);

    Mono<Void> updateAuthorInformation(UUID userId, String nickname, String name, String surName, String city, LocalDate birthDay, String description);

    Mono<Void> deleteMainPageInformation(UUID userId);
}
