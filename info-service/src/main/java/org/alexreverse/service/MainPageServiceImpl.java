package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.dto.AuthorInformationDto;
import org.alexreverse.dto.EducationDto;
import org.alexreverse.dto.MainPageResponse;
import org.alexreverse.dto.WorkExperienceDto;
import org.alexreverse.dto.mapper.InfoMapper;
import org.alexreverse.entity.AuthorInformation;
import org.alexreverse.repository.AuthorInformationRepository;
import org.alexreverse.repository.EducationRepository;
import org.alexreverse.repository.WorkExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {

    private final AuthorInformationRepository authorInformationRepository;

    private final WorkExperienceRepository workExperienceRepository;

    private final EducationRepository educationRepository;

    @Autowired
    private InfoMapper infoMapper;

    @Override
    public Mono<MainPageResponse> findMainPage(UUID uuid) {
        return authorInformationRepository.findById(uuid)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "")))
                .flatMap(authorInformation -> {

                    Mono<List<EducationDto>> educationList = educationRepository
                            .findAllByUserId(authorInformation.getUserId())
                            .map(infoMapper::educationToDto)
                            .collectList()
                            .defaultIfEmpty(new ArrayList<>());

                    Mono<List<WorkExperienceDto>> workExpList = workExperienceRepository
                            .findAllByUserId(authorInformation.getUserId())
                            .map(infoMapper::workToDto)
                            .collectList()
                            .defaultIfEmpty(new ArrayList<>());

                    return Mono.zip(educationList, workExpList)
                            .map(tuple -> new MainPageResponse(
                                    authorInformation.getUserId(),
                                    authorInformation.getNickname(),
                                    authorInformation.getName(),
                                    authorInformation.getSurName(),
                                    authorInformation.getCity(),
                                    authorInformation.getBirthDay(),
                                    authorInformation.getDescription(),
                                    authorInformation.getRegistrationDate(),
                                    tuple.getT1(),
                                    tuple.getT2()
                            ));
                });
    }

    @Override
    public Mono<AuthorInformationDto> findAuthorInformation(UUID userId) {
        return authorInformationRepository.findById(userId).map(infoMapper::authorToDto);
    }

    @Override
    public Flux<EducationDto> findEducation(UUID uuid) {
        return educationRepository.findAllByUserId(uuid).map(infoMapper::educationToDto);
    }

    @Override
    public Flux<WorkExperienceDto> findWorkExperience(UUID uuid) {
        return workExperienceRepository.findAllByUserId(uuid).map(infoMapper::workToDto);
    }

//    @Override
//    public Flux<AuthorInformationDto> findAllAuthorInformationByNameOrSurNameOrNickname(String filter) {
//        if (filter != null && !filter.isBlank()) {
//            return authorInformationRepository.findAllByName("%" + filter + "%")
//                    .map(list -> );
//        } else {
//            return authorInformationRepository.findAll();
//        }
//    }

    @Override
    public Mono<AuthorInformationDto> createAuthorInformation(UUID userId, String nickname, String name, String surName, String city, LocalDate birthDay, String description) {
        return findAuthorInformation(userId)
                .switchIfEmpty(authorInformationRepository.save(new AuthorInformation(userId, nickname, name, surName,
                city, birthDay, description, LocalDateTime.now(), true))
                        .map(infoMapper::authorToDto));
    }

    @Override
    public Mono<Void> updateAuthorInformation(UUID userId, String nickname, String name, String surName, String city, LocalDate birthDay, String description) {
        return authorInformationRepository.findById(userId)
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
    @Transactional
    public Mono<Void> deleteMainPageInformation(UUID userId) {
        return authorInformationRepository.deleteByUserId(userId)
                .then(educationRepository.deleteByUserId(userId))
                .then(workExperienceRepository.deleteByUserId(userId));
    }
}
