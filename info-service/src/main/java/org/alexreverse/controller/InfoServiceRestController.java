package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.AuthorInformationPayload;
import org.alexreverse.controller.payload.UpdateAuthorInformationPayload;
import org.alexreverse.entity.AuthorInformation;
import org.alexreverse.service.AuthorInformationService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("main-page")
public class InfoServiceRestController {

    private final AuthorInformationService authorInformationService;

    private final MessageSource messageSource;

    @ModelAttribute(name = "page", binding = false)
    public Mono<AuthorInformation> getMainPage(JwtAuthenticationToken auth) {
        return this.authorInformationService.findAuthorInformation(UUID.fromString(auth.getName()))
                .switchIfEmpty(Mono.error(new NoSuchElementException("infoservice.mainpage.errors.page_not_found")));
    }

    @GetMapping
    public Mono<AuthorInformation> findAuthorInformation(@ModelAttribute("page") AuthorInformation mainPage) {
        return authorInformationService.findAuthorInformation(mainPage.getUserId());
    }

    @PostMapping
    public Mono<ResponseEntity<AuthorInformation>> createAuthorInformation(JwtAuthenticationToken auth,
                                                         @Valid @RequestBody Mono<AuthorInformationPayload> mainPagePayloadMono,
                                                         UriComponentsBuilder uriComponentsBuilder) {
        return mainPagePayloadMono
                .flatMap(authorInformationPayload -> this.authorInformationService.createAuthorInformation(
                        UUID.fromString(auth.getName()),
                        authorInformationPayload.nickname(), authorInformationPayload.name(), authorInformationPayload.surName(),
                        authorInformationPayload.city(), authorInformationPayload.birthDay(), authorInformationPayload.description()))
                .map(mainPage -> ResponseEntity.created(uriComponentsBuilder.replacePath("/main-page")
                        .build(mainPage.getUserId())).body(mainPage))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteMainPage(JwtAuthenticationToken auth) {
        return this.authorInformationService.deleteAuthorInformation(UUID.fromString(auth.getToken().getClaimAsString(StandardClaimNames.SUB)))
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping
    public Mono<ResponseEntity<Void>> updateAuthorInformation(JwtAuthenticationToken auth,
                                                     @Valid @RequestBody UpdateAuthorInformationPayload payload) {
        return this.authorInformationService.findAuthorInformation(UUID.fromString(auth.getToken().getClaimAsString(StandardClaimNames.SUB)))
                .flatMap(unused ->
                authorInformationService.updateAuthorInformation(UUID.fromString(auth.getToken().getClaimAsString(StandardClaimNames.SUB)),
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
