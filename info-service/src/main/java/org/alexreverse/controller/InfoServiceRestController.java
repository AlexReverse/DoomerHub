package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.MainPagePayload;
import org.alexreverse.controller.payload.UpdatePagePayload;
import org.alexreverse.entity.MainPage;
import org.alexreverse.service.PageService;
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

    private final PageService pageService;
    private final MessageSource messageSource;

    @ModelAttribute(name = "page", binding = false)
    public Mono<MainPage> getMainPage(JwtAuthenticationToken auth) {
        return this.pageService.findMainPage(UUID.fromString(auth.getName()))
                .switchIfEmpty(Mono.error(new NoSuchElementException("infoservice.mainpage.errors.page_not_found")));
    }

    @GetMapping
    public Mono<MainPage> findMainPage(@ModelAttribute("page") MainPage mainPage) {
        return pageService.findMainPage(mainPage.getUserId());
    }

    @PostMapping
    public Mono<ResponseEntity<MainPage>> createMainPage(JwtAuthenticationToken auth,
                                                         @Valid @RequestBody Mono<MainPagePayload> mainPagePayloadMono,
                                                         UriComponentsBuilder uriComponentsBuilder) {
        return mainPagePayloadMono
                .flatMap(mainPagePayload -> this.pageService.createMainPage(
                        UUID.fromString(auth.getName()),
                        mainPagePayload.nickname(), mainPagePayload.name(), mainPagePayload.surName(),
                        mainPagePayload.city(), mainPagePayload.birthDay(), mainPagePayload.description()))
                .map(mainPage -> ResponseEntity.created(uriComponentsBuilder.replacePath("/main-page")
                        .build(mainPage.getUserId())).body(mainPage))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteMainPage(JwtAuthenticationToken auth) {
        return this.pageService.deleteMainPage(UUID.fromString(auth.getToken().getClaimAsString(StandardClaimNames.SUB)))
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping
    public Mono<ResponseEntity<Void>> updateMainPage(JwtAuthenticationToken auth,
                                                     @Valid @RequestBody UpdatePagePayload payload) {
        return this.pageService.findMainPage(UUID.fromString(auth.getToken().getClaimAsString(StandardClaimNames.SUB)))
                .flatMap(unused ->
                pageService.updateMainPage(UUID.fromString(auth.getToken().getClaimAsString(StandardClaimNames.SUB)),
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
