package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.NewPostPayload;
import org.alexreverse.entity.Post;
import org.alexreverse.service.PostsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("search-api/posts")
public class PostsRestController {

    private final PostsService postsService;

    @GetMapping
    public Flux<Post> findPosts(@RequestParam(name = "filter", required = false) String filter,
                                @RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("postDate"));
        return this.postsService.findAllPosts(filter, pageable);
    }

    @GetMapping("/by-user")
    public Flux<Post> findPostsByUser(@AuthenticationPrincipal Jwt jwt,
                                      @RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.postsService.findAllPostsByUser(jwt.getSubject(), pageable);
    }

    @PostMapping
    public Mono<ResponseEntity<Post>> createPost(@AuthenticationPrincipal Jwt jwt,
                                                 @Valid @RequestBody Mono<NewPostPayload> newPostPayloadMono,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        return newPostPayloadMono
                .flatMap(newPostPayload -> this.postsService.createPost(newPostPayload.title(),
                        newPostPayload.description(), jwt.getSubject()))
                .map(postService -> ResponseEntity
                            .created(uriComponentsBuilder.replacePath("/search-api/posts/{postId}")
                                    .build(postService.getId()))
                            .body(postService))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}

