package org.alexreverse.controller;

import org.alexreverse.controller.payload.NewPostTranslation;
import org.alexreverse.service.PostTranslationService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("translate/to-english")
public class AiController {

    private final PostTranslationService postTranslationService;

    private final ChatClient client;

    public AiController(PostTranslationService postTranslationService, ChatClient.Builder builder) {
        this.postTranslationService = postTranslationService;
        this.client = builder.build();
    }

    @PostMapping
    public ResponseEntity<?> getTranslateToEnglish(@Valid @RequestBody NewPostTranslation newPostTranslationMono) {
        try {
            return ResponseEntity.ok()
                    .body(this.postTranslationService.createPostTranslation(newPostTranslationMono.postId(),
                            client.prompt().user("Переведи текст на Английский: " +
                                    newPostTranslationMono.description()).call().content()));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getCause());
        }
    }
}
