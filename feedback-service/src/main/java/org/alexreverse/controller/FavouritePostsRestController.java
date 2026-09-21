package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.NewFavouritePostPayload;
import org.alexreverse.dto.FavouritePostDto;
import org.alexreverse.service.FavouritePostsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("favourite")
@RequiredArgsConstructor
public class FavouritePostsRestController {

    private final FavouritePostsService favouritePostsService;

    @GetMapping("{userName}")
    public Flux<FavouritePostDto> findFavouritePosts(@PathVariable("userName") String userName) {
        return favouritePostsService.findFavouritePosts(userName);
    }

    @GetMapping("{userName}/by-post-id/{postId:\\d+}")
    public Mono<FavouritePostDto> findFavouritePostByPostId(@PathVariable("postId") Long postId,
                                                         @PathVariable("userName") String userName) {
        return favouritePostsService.findFavouritePostByPost(postId, userName);
    }

    @PostMapping("{userName}")
    public Mono<ResponseEntity<FavouritePostDto>> addPostToFavourites(
            @Valid @RequestBody NewFavouritePostPayload payload,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        return favouritePostsService.createFavouritePost(payload)
                .map(favouritePost -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("favourite/{userName}")
                                .build(favouritePost.userName()))
                        .body(favouritePost))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("{userName}/by-post-id/{postId:\\d+}")
    public Mono<ResponseEntity<Void>> removePostFromFavourites(@PathVariable("postId") Long postId,
                                                               @PathVariable("userName") String userName) {
        return favouritePostsService.removePostFromFavourites(postId, userName)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @DeleteMapping("by-post-id/{postId:\\d+}")
    public Mono<ResponseEntity<Void>> deleteFavouritesFromPost(@PathVariable("postId") Long postId) {
        return favouritePostsService.deleteFavouritesFromPost(postId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
