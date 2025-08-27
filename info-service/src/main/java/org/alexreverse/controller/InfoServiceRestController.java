package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.MainPagePayload;
import org.alexreverse.controller.payload.UpdatePagePayload;
import org.alexreverse.entity.MainPage;
import org.alexreverse.service.PageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("main-page/{userId}")
public class InfoServiceRestController {

    private final PageService pageService;

    @GetMapping
    public Mono<MainPage> findMainPage(@PathVariable("userId") UUID uuid) {
        return pageService.findMainPage(uuid);
    }

    @PostMapping
    public Mono<ResponseEntity<MainPage>> createMainPage(@PathVariable("userId") UUID uuid,
                                                         @Valid @RequestBody Mono<MainPagePayload> mainPagePayloadMono,
                                                         UriComponentsBuilder uriComponentsBuilder) {
        return mainPagePayloadMono
                .flatMap(mainPagePayload -> this.pageService.createMainPage(uuid,
                        mainPagePayload.nickname(), mainPagePayload.name(), mainPagePayload.surName(),
                        mainPagePayload.city(), mainPagePayload.birthDay(), mainPagePayload.description()))
                .map(mainPage -> ResponseEntity.created(uriComponentsBuilder.replacePath("/main-page/{userId}")
                        .build(mainPage.getUserId())).body(mainPage))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteMainPage(@RequestParam(name = "uuid", required = true) UUID uuid) {
        return this.pageService.deleteMainPage(uuid).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping
    public Mono<ResponseEntity<Void>> updateMainPage(@RequestParam(name = "uuid", required = true) UUID uuid,
                                                     @Valid @RequestBody UpdatePagePayload payload) {
        return this.pageService.findMainPage(uuid).flatMap(unused ->
                pageService.updateMainPage(uuid, payload.nickname(), payload.name(), payload.surName(),
                        payload.city(), payload.birthDay(), payload.description())
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
