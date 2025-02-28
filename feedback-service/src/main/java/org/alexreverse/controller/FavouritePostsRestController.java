package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.NewFavouritePostPayload;
import org.alexreverse.entity.FavouritePost;
import org.alexreverse.service.FavouritePostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("feedback-api/favourite-posts")
@RequiredArgsConstructor
public class FavouritePostsRestController {

    private final FavouritePostsService favouritePostsService;

    @GetMapping
    public Flux<FavouritePost> findFavouritePosts() {
        return this.favouritePostsService.findFavouritePosts();
    }

    @GetMapping("by-post-id/{postId:\\d+}")
    public Mono<FavouritePost> findFavouritePostByPostId(@PathVariable("postId") Integer postId) {
        return this.favouritePostsService.findFavouritePostByPost(postId);
    }

    @PostMapping
    public Mono<ResponseEntity<FavouritePost>> createFavouritePost(@Valid @RequestBody Mono<NewFavouritePostPayload>
                                                                               payloadMono,
                                                                   UriComponentsBuilder uriComponentsBuilder) {
        return payloadMono.flatMap(payload -> this.favouritePostsService.addPostToFavourites(payload.id()))
                .map(favouritePost -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("feedback-api/favourite-posts/{id}")
                                .build(favouritePost.getId()))
                        .body(favouritePost));
    }

    @DeleteMapping("by-post-id/{postId:\\d+}")
    public Mono<ResponseEntity<Void>> removePostFromFavourites(@PathVariable("postId") int postId) {
        return this.favouritePostsService.removePostFromFavourites(postId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
