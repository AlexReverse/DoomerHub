package org.alexreverse.controller;

import lombok.RequiredArgsConstructor;
import org.alexreverse.dto.PostGetTranslate;
import org.alexreverse.dto.PostReturnTranslate;
import org.alexreverse.service.AiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/generate")
    public PostReturnTranslate generate(@RequestBody PostGetTranslate text) {
        return aiService.getTranslate(text);
    }
}
