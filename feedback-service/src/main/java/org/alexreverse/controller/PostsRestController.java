package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.NewPostPayload;
import org.alexreverse.entity.Post;
import org.alexreverse.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("search-api/posts")
public class PostsRestController {

    private final PostService postService;

    @GetMapping
    public Flux<Post> findPosts(@RequestParam(name = "filter", required = false) String filter) {
        return this.postService.findAllPosts(filter);
    }

    @PostMapping
    public Mono<ResponseEntity<Post>> createPost(@Valid @RequestBody Mono<NewPostPayload> newPostPayloadMono,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        return newPostPayloadMono
                .flatMap(newPostPayload -> this.postService.createPost(newPostPayload.title(),
                        newPostPayload.description(), newPostPayload.userId(), newPostPayload.englishTranslation()))
                .map(postService -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("/search-api/posts/{postId}")
                                .build(postService.getId()))
                        .body(postService))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}

