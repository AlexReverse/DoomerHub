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
@RequestMapping("favourite/{user}")
@RequiredArgsConstructor
public class FavouritePostsRestController {

    private final FavouritePostsService favouritePostsService;

    @GetMapping
    public Flux<FavouritePost> findFavouritePosts(@PathVariable("user") String user) {
        return this.favouritePostsService.findFavouritePosts(user);
    }
    @GetMapping("by-post-id/{postId:\\d+}")
    public Mono<FavouritePost> findFavouritePostByPostId(@PathVariable("postId") Integer postId,
                                                         @PathVariable("user") String user) {
        return this.favouritePostsService.findFavouritePostByPost(postId, user);
    }
    @PostMapping
    public Mono<ResponseEntity<FavouritePost>> addPostToFavourites(@Valid @RequestBody Mono<NewFavouritePostPayload>
                                                                               payloadMono,
                                                                   UriComponentsBuilder uriComponentsBuilder) {
        return payloadMono.flatMap(payload -> this.favouritePostsService.addPostToFavourites(payload.postId(),
                        payload.user()))
                .map(favouritePost -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("favourite/{idFavouritePost}")
                                .build(favouritePost.getIdFavouritePost()))
                        .body(favouritePost))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("by-post-id/{postId:\\d+}")
    public Mono<ResponseEntity<Void>> removePostFromFavourites(@PathVariable("postId") int postId, String user) {
        return this.favouritePostsService.removePostFromFavourites(postId, user)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
