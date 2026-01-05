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
import org.springframework.web.reactive.function.client.WebClientResponseException;
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

    @GetMapping("search")
    public Mono<String> getMainPages(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filte", filter);
        return this.mainPageClient.findAllMainPage(filter)
                .collectList()
                .doOnNext(mainPages -> model.addAttribute("mainPages", mainPages))
                .thenReturn("main-page/search");
    }

    @GetMapping
    @ModelAttribute(name = "mainPage")
    public Mono<MainPage> getMainPage() {
        return this.mainPageClient.findMainPage();
    }

    @GetMapping("create")
    public Mono<String> getMainPageCreate() {
        return Mono.just("main-page/create");
    }

    @GetMapping("edit")
    @ModelAttribute(name = "mainPage", binding = false)
    public Mono<MainPage> getMainPageEdit() {
        return this.mainPageClient.findMainPage();
    }

    @PostMapping("create")
    public Mono<String> createMainPage(MainPagePayload pagePayload) {
        try {
            return this.mainPageClient.createMainPage(pagePayload.nickname(), pagePayload.name(), pagePayload.surName(),
                    pagePayload.city(), pagePayload.birthDay(), pagePayload.description()).thenReturn("redirect:/main-page");
        } catch (Exception exception) {
            log.info(exception.getMessage());
            return Mono.just("main-page/create");
        }
    }

    @PostMapping("edit")
    public Mono<String> updateMainPage(MainPagePayload pagePayload, OAuth2AuthenticationToken token) {
        try {
            if (token.getPrincipal().getAttribute("sub") == null) throw new AccessDeniedException("User not authorized");
            return this.mainPageClient.updateMainPage(pagePayload.nickname(), pagePayload.name(), pagePayload.surName(),
                    pagePayload.city(), pagePayload.birthDay(), pagePayload.description()).thenReturn("redirect:/main-page");
        } catch (AccessDeniedException exception) {
            log.warn(exception.getMessage());
            return Mono.just("redirect:/main-page/create");
        } catch (Exception exception) {
            log.info(exception.getMessage());
            return Mono.just("redirect:/main-page/edit");
        }
    }

    @PostMapping("delete")
    public Mono<String> deleteMainPage() {
        return this.mainPageClient.deleteMainPage().thenReturn("redirect:/");
    }

    @ExceptionHandler({NoSuchElementException.class, WebClientResponseException.NotFound.class})
    public Mono<String> handleNoSuchElementException(Exception exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return Mono.just("redirect:/main-page/create");
    }

    @ModelAttribute
    public Mono<CsrfToken> loadCsrfToken(ServerWebExchange exchange) {
        return exchange.<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName())
                .doOnSuccess(token -> exchange.getAttributes()
                        .put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, token));
    }
}
