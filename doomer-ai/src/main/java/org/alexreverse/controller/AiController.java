package org.alexreverse.controller;

import lombok.RequiredArgsConstructor;
import org.alexreverse.dto.PostGetTranslate;
import org.alexreverse.dto.PostReturnTranslate;
import org.alexreverse.service.AiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("translate/to-english")
public class AiController {

    private final AiService aiService;

    @PostMapping
    public PostReturnTranslate translateToEnglish(@RequestBody PostGetTranslate text) {
        return aiService.getTranslateToEnglish(text);
    }
}
