package org.alexreverse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexreverse.client.MainPageClient;
import org.alexreverse.entity.MainPage;
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.ui.Model;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("main-page")
@Slf4j
public class MainPageController {

    private final MainPageClient mainPageClient;

    @ModelAttribute(name = "mainPage")
    public Mono<MainPage> loadMainPage() {
        return this.mainPageClient.findMainPage().switchIfEmpty(Mono.error(new NoSuchElementException()));
    }

    @GetMapping("search")
    public Mono<String> getMainPages(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filte", filter);
        return this.mainPageClient.findAllMainPage(filter)
                .collectList()
                .doOnNext(mainPages -> model.addAttribute("mainPages", mainPages))
                .thenReturn("main-page/search");
    }

    @GetMapping
    public Mono<String> getMainPage() {
        return Mono.just("main-page");
    }

    @GetMapping("create")
    public Mono<String> getPostEditPage() {
        return Mono.just("main-page/create");
    }

    //public Mono<String> createMainPage() {}

    public Mono<String> deleteMainPage() {
        try {
            this.mainPageClient.deleteMainPage();
            return Mono.just("");
        } catch (NoSuchElementException e) {
            log.info(e.getMessage());
            return Mono.just("");
        }
    }

    @ModelAttribute
    public Mono<CsrfToken> loadCsrfToken(ServerWebExchange exchange) {
        return exchange.<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName())
                .doOnSuccess(token -> exchange.getAttributes()
                        .put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, token));
    }
}
