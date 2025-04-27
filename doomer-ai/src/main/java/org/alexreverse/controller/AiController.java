package org.alexreverse.controller;

import lombok.RequiredArgsConstructor;
import org.alexreverse.dto.PostGetTranslate;
import org.alexreverse.entity.PostsTranslation;
import org.alexreverse.service.AiService;
import org.alexreverse.service.PostsTranslationService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final PostsTranslationService postsTranslationService;

    private final AiService aiService;

    @PostMapping("/translate-to-english")
    public Mono<Long> translateToEnglish(@RequestBody PostGetTranslate text) {
        return this.postsTranslationService.createPostTranslation(aiService.getTranslateToEnglish(text).translate())
                .map(PostsTranslation::getId);
    }
}
