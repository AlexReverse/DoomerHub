package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.NewFavouritePostPayload;
import org.alexreverse.entity.FavouritePost;
import org.alexreverse.service.FavouritePostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("favourite/{userName}")
@RequiredArgsConstructor
public class FavouritePostsRestController {

    private final FavouritePostsService favouritePostsService;

    @GetMapping
    public Flux<FavouritePost> findFavouritePosts(@PathVariable("userName") String userName) {
        return this.favouritePostsService.findFavouritePosts(userName);
    }
    @GetMapping("by-post-id/{postId:\\d+}")
    public Mono<FavouritePost> findFavouritePostByPostId(@PathVariable("postId") Long postId,
                                                         @PathVariable("userName") String userName) {
        return this.favouritePostsService.findFavouritePostByPost(postId, userName);
    }
    @PostMapping
    public Mono<ResponseEntity<FavouritePost>> addPostToFavourites(
            @Valid @RequestBody Mono<NewFavouritePostPayload> payloadMono,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        return payloadMono
                .flatMap(payload -> this.favouritePostsService.createFavouritePost(payload.postId(), payload.userName()))
                .map(favouritePost -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("favourite/{userName}")
                                .build(favouritePost.getUserName()))
                        .body(favouritePost))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("by-post-id/{postId:\\d+}")
    public Mono<ResponseEntity<Void>> removePostFromFavourites(@PathVariable("postId") Long postId,
                                                               @PathVariable("userName") String userName) {
        return this.favouritePostsService.removePostFromFavourites(postId, userName)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
