package org.alexreverse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexreverse.client.MainPageClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.springframework.ui.Model;

//@Controller
@RequiredArgsConstructor
@RequestMapping("main-page")
@Slf4j
public class MainPageController {

    private final MainPageClient mainPageClient;

    @ModelAttribute
    public Mono<String> getMainPage() {
        return Mono.just("main-page");
    }

    @GetMapping("search")
    public Mono<String> getMainPages(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filte", filter);
        return this.mainPageClient.findAllMainPage(filter)
                .collectList()
                .doOnNext(mainPages -> model.addAttribute("mainPages", mainPages))
                .thenReturn("main-page/search");
    }

    //public Mono<String> createMainPage() {}
}
