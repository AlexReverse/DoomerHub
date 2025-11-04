package org.alexreverse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexreverse.client.MainPageClient;
import org.alexreverse.client.payload.MainPagePayload;
import org.alexreverse.entity.MainPage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

    @PostMapping
    public Mono<String> createMainPage(MainPagePayload pagePayload) {
        try {
            return this.mainPageClient.createMainPage(pagePayload.nickname(), pagePayload.name(), pagePayload.surName(),
                    pagePayload.city(), pagePayload.birthDay(), pagePayload.description()).thenReturn("redirect:/main-page");
        } catch (Exception exception) {
            log.info(exception.getMessage());
            return Mono.just("main-page/create");
        }
    }

    @PatchMapping("edit")
    public Mono<String> updateMainPage(@ModelAttribute(name = "mainPage", binding = false) MainPage mainPage,
                                       MainPagePayload pagePayload, OAuth2AuthenticationToken token) {
        try {
            if (!mainPage.userId().equals(token.getPrincipal().getAttribute("sub"))) {
                throw new AccessDeniedException("The user %s is not authorized or has no rights for %s"
                        .formatted(token.getPrincipal().getAttribute("sub"), mainPage.userId()));
            }
            return this.mainPageClient.updateMainPage(pagePayload.nickname(), pagePayload.name(), pagePayload.surName(),
                    pagePayload.city(), pagePayload.birthDay(), pagePayload.description()).thenReturn("redirect:/main-page");
        } catch (AccessDeniedException exception) {
            log.warn(exception.getMessage());
            return Mono.just("redirect:/main-page");
        } catch (Exception exception) {
            log.info(exception.getMessage());
            return Mono.just("redirect:/main-page/edit");
        }
    }

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
