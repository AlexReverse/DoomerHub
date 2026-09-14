package org.alexreverse.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.AuthorInformationPayload;
import org.alexreverse.controller.payload.EducationPayload;
import org.alexreverse.controller.payload.WorkExperiencePayload;
import org.alexreverse.dto.AuthorInformationDto;
import org.alexreverse.dto.EducationDto;
import org.alexreverse.dto.MainPageResponse;
import org.alexreverse.dto.WorkExperienceDto;
import org.alexreverse.dto.mapper.InfoMapper;
import org.alexreverse.repository.AuthorInformationRepository;
import org.alexreverse.repository.EducationRepository;
import org.alexreverse.repository.WorkExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
    public Mono<AuthorInformationDto> createAuthorInformation(UUID userId, AuthorInformationPayload payload) {
        return findAuthorInformation(userId)
                .switchIfEmpty(authorInformationRepository
                        .save(infoMapper.authorPayloadToEntity(userId, payload, LocalDateTime.now()))
                        .map(infoMapper::authorToDto));
    }

    @Override
    public Flux<EducationDto> createEducationsInformation(UUID userId, List<EducationPayload> payloads) {
        return Flux.fromIterable(payloads)
                .map(payload -> infoMapper.payloadToEducation(userId, payload))
                .flatMap(educationRepository::save)
                .map(infoMapper::educationToDto);
    }

    @Override
    public Flux<WorkExperienceDto> createWorkExperiences(UUID userId, List<WorkExperiencePayload> payloads) {
        return Flux.fromIterable(payloads)
                .map(payload -> infoMapper.payloadToWorkExperience(userId, payload))
                .flatMap(workExperienceRepository::save)
                .map(infoMapper::workToDto);
    }

    @Override
    public Mono<AuthorInformationDto> updateAuthorInformation(UUID userId, AuthorInformationPayload payload) {
        return authorInformationRepository.findById(userId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("")))
                .map(entity -> {
                    infoMapper.authorPayloadPatchEntity(payload, entity);
                    return entity;
                })
                .flatMap(authorInformationRepository::save)
                .map(infoMapper::authorToDto);
    }

    @Override
    public Mono<Void> deleteMainPageInformation(UUID userId) {
        return Mono.when(authorInformationRepository.deleteByUserId(userId),
                        educationRepository.deleteAllByUserId(userId),
                        workExperienceRepository.deleteAllByUserId(userId)
        );
    }

    @Override
    public Mono<Void> deleteEducationInformation(Long id, UUID userId) {
        educationRepository.findById(id).switchIfEmpty(Mono.error(new NoSuchElementException()));
        return educationRepository.deleteByIdAndUserId(id, userId);
    }

    @Override
    public Mono<Void> deleteWorkExperience(Long id, UUID userId) {
        workExperienceRepository.findById(id).switchIfEmpty(Mono.error(new NoSuchElementException()));
        return workExperienceRepository.deleteByIdAndUserId(id, userId);
    }
}
