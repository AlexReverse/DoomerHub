package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.NewPostReviewPayload;
import org.alexreverse.entity.PostReview;
import org.alexreverse.service.PostReviewsService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("feedback-api/post-reviews")
@RequiredArgsConstructor
public class PostReviewsRestController {

    private final PostReviewsService postReviewsService;

    @GetMapping("by-post-id/{postId:\\d+}")
    public Flux<PostReview> findPostReviewsByPostId(@PathVariable("postId") Long postId) {
        return this.postReviewsService.findPostReviewsByPostId(postId);
    }

    @PostMapping
    public Mono<ResponseEntity<PostReview>> createPostReview(@Valid @RequestBody Mono<NewPostReviewPayload> payloadMono,
                                                             UriComponentsBuilder uriComponentsBuilder) {
        return payloadMono
                .flatMap(payload -> this.postReviewsService.createPostReview(payload.postId(), payload.review(),
                        payload.userName()))
                .map(postReview -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("/feedback-api/post-reviews/{id}")
                                .build(postReview.getId()))
                        .body(postReview));
    }

}
