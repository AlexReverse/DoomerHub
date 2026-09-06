package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.AuthorInformation;
import org.alexreverse.repository.AuthorInformationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorInformationServiceImpl implements AuthorInformationService {

    private final AuthorInformationRepository authorInformationRepository;

    @Override
    public Mono<AuthorInformation> findAuthorInformation(UUID userId) {
        return this.authorInformationRepository.findById(userId);
    }

    @Override
    public Flux<AuthorInformation> findAllAuthorInformationByNameOrSurNameOrNickname(String filter) {
        if (filter != null && !filter.isBlank()) {
            return this.authorInformationRepository.findAllByName("%" + filter + "%");
        } else {
            return this.authorInformationRepository.findAll();
        }
    }

    @Override
    public Mono<AuthorInformation> createAuthorInformation(UUID userId, String nickname, String name, String surName, String city, LocalDate birthDay, String description) {
        return findAuthorInformation(userId).switchIfEmpty(this.authorInformationRepository.save(new AuthorInformation(userId, nickname, name, surName,
                        city, birthDay,description, LocalDateTime.now(), true)));
    }

    @Override
    public Mono<Void> updateAuthorInformation(UUID userId, String nickname, String name, String surName, String city, LocalDate birthDay, String description) {
        return this.authorInformationRepository.findById(userId)
                .flatMap(mainPage -> {
                    mainPage.setNickname(nickname);
                    mainPage.setName(name);
                    mainPage.setSurName(surName);
                    mainPage.setCity(city);
                    mainPage.setBirthDay(birthDay);
                    mainPage.setDescription(description);
                    mainPage.setNew(false);
                    return authorInformationRepository.save(mainPage);
                })
                .map(page -> new ResponseEntity<>(page, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND))
                .then();
    }

    @Override
    public Mono<Void> deleteAuthorInformation(UUID userId) {
        return this.authorInformationRepository.deleteByUserId(userId);
    }
}
