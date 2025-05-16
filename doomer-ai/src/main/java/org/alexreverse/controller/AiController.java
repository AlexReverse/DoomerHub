package org.alexreverse.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.NewPostTranslation;
import org.alexreverse.entity.PostTranslation;
import org.alexreverse.service.PostTranslationService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity<PostTranslation>> getTranslateToEnglish(@Valid @RequestBody Mono<NewPostTranslation> newPostTranslationMono) {
        return newPostTranslationMono.flatMap(newPostTranslation ->
                this.postTranslationService.createPostTranslation(newPostTranslation.postId(),
                        client.prompt().user("Переведи текст на Английский: " +
                                newPostTranslation.description()).call().content()))
                .map(postTranslation -> ResponseEntity.ok().body(postTranslation))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
