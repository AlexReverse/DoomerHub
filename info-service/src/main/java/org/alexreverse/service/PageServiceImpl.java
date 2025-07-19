package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.MainPage;
import org.alexreverse.repository.PageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    @Override
    public Mono<MainPage> findMainPage(UUID userId) {
        return this.pageRepository.findById(userId);
    }

    @Override
    public Flux<MainPage> findAllMainPageByNameOrSurNameOrNickname(String filter) {
        if (filter != null && !filter.isBlank()) {
            return this.pageRepository.findAllMainPageByNameOrSurNameOrNickname("%" + filter + "%");
        } else {
            return this.pageRepository.findAll();
        }
    }

    @Override
    public Mono<MainPage> createMainPage(UUID userId, String nickname, String name, String surName, String city, Byte age, String description) {
        return this.pageRepository.save(new MainPage(userId, nickname, name, surName, city, age, description, LocalDateTime.now()));
    }

    @Override
    public Mono<Void> updateMainPage(UUID userId, String nickname, String name, String surName, String city, Byte age, String description) {
        return this.pageRepository.findById(userId)
                .flatMap(mainPage -> {
                    mainPage.setNickname(nickname);
                    mainPage.setName(name);
                    mainPage.setSurName(surName);
                    mainPage.setCity(city);
                    mainPage.setAge(age);
                    mainPage.setDescription(description);
                    return pageRepository.save(mainPage);
                })
                .map(page -> new ResponseEntity<>(page, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND))
                .then();
    }

    @Override
    public Mono<Void> deleteMainPage(UUID userId) {
        return this.pageRepository.deleteById(userId);
    }
}
