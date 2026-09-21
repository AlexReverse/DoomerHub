package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.NewPostPayload;
import org.alexreverse.dto.PostDto;
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

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("search-api/posts")
public class PostsRestController {

    private final PostsService postsService;

    @GetMapping
    public Flux<PostDto> findPosts(@RequestParam(name = "filter", required = false) String filter,
                                   @RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("postDate"));
        return postsService.findAllPosts(filter, pageable);
    }

    @GetMapping("/by-user")
    public Flux<PostDto> findPostsByUser(@AuthenticationPrincipal Jwt jwt,
                                      @RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return postsService.findAllPostsByUser(UUID.fromString(jwt.getSubject()), pageable);
    }

    @PostMapping
    public Mono<ResponseEntity<PostDto>> createPost(@AuthenticationPrincipal Jwt jwt,
                                                    @Valid @RequestBody NewPostPayload payload,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        return postsService.createPost(payload,
                        UUID.fromString(jwt.getSubject()))
                .map(postService -> ResponseEntity
                            .created(uriComponentsBuilder.replacePath("/search-api/posts/{postId}")
                                    .build(postService.id()))
                            .body(postService))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}

