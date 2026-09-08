package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.AuthorInformationPayload;
import org.alexreverse.dto.AuthorInformationDto;
import org.alexreverse.dto.EducationDto;
import org.alexreverse.dto.MainPageResponse;
import org.alexreverse.dto.WorkExperienceDto;
import org.alexreverse.service.MainPageService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("main-page")
public class MainPageServiceRestController {

    private final MainPageService mainPageService;

    private final MessageSource messageSource;

    @ModelAttribute(name = "page", binding = false)
    public Mono<MainPageResponse> getMainPage(JwtAuthenticationToken auth) {
        return mainPageService.findMainPage(UUID.fromString(auth.getName()))
                .switchIfEmpty(Mono.error(new NoSuchElementException("infoservice.mainpage.errors.page_not_found")));
    }

    @GetMapping
    public Mono<MainPageResponse> getMainPage(@ModelAttribute("page") Mono<MainPageResponse> response,
                                              JwtAuthenticationToken auth) {
        return response.switchIfEmpty(mainPageService.findMainPage(UUID.fromString(auth.getName()))
                .switchIfEmpty(Mono.error(new NoSuchElementException("infoservice.mainpage.errors.page_not_found"))));
    }

    @GetMapping("information")
    public Mono<AuthorInformationDto> getAuthorInformation(@ModelAttribute("page") MainPageResponse response) {
        return mainPageService.findAuthorInformation(response.userId());
    }

    @GetMapping("education")
    public Flux<EducationDto> getEducation(@ModelAttribute("page") MainPageResponse response) {
        return mainPageService.findEducation(response.userId());
    }

    @GetMapping("work-experience")
    public Flux<WorkExperienceDto> getWorkExperience(@ModelAttribute("page") MainPageResponse response) {
        return mainPageService.findWorkExperience(response.userId());
    }

    @PostMapping
    public Mono<ResponseEntity<AuthorInformationDto>> createAuthorInformation(JwtAuthenticationToken auth,
                                                                              @Valid @RequestBody Mono<AuthorInformationPayload> payload,
                                                                              UriComponentsBuilder uriComponentsBuilder) {
        UUID userId = UUID.fromString(auth.getName());

        return payload
                .flatMap(authorInformationPayload -> mainPageService.createAuthorInformation(
                        userId,
                        authorInformationPayload.nickname(),
                        authorInformationPayload.name(),
                        authorInformationPayload.surName(),
                        authorInformationPayload.city(),
                        authorInformationPayload.birthDay(),
                        authorInformationPayload.description()))
                .map(mainPage -> {
                    URI location = uriComponentsBuilder
                            .replacePath("/main-page")
                            .buildAndExpand()
                            .toUri();
                    return ResponseEntity
                            .created(location)
                            .body(mainPage);
                });
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteMainPage(JwtAuthenticationToken auth) {
        return this.mainPageService.deleteMainPageInformation(UUID.fromString(auth.getToken().getClaimAsString(StandardClaimNames.SUB)))
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping
    public Mono<ResponseEntity<Void>> updateAuthorInformation(JwtAuthenticationToken auth,
                                                              @Valid @RequestBody AuthorInformationPayload payload) {
        return this.mainPageService.findAuthorInformation(UUID.fromString(auth.getToken().getClaimAsString(StandardClaimNames.SUB)))
                .flatMap(unused ->
                        mainPageService.updateAuthorInformation(UUID.fromString(auth.getToken().getClaimAsString(StandardClaimNames.SUB)),
                                        payload.nickname(), payload.name(), payload.surName(),
                                        payload.city(), payload.birthDay(), payload.description())
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)));
    }
}
