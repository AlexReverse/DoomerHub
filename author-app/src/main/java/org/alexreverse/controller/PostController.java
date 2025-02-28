package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexreverse.client.FavouritePostsClient;
import org.alexreverse.client.PostReviewsClient;
import org.alexreverse.client.PostsClient;
import org.alexreverse.client.exception.ClientBadRequestException;
import org.alexreverse.controller.payload.NewPostReviewPayload;
import org.alexreverse.entity.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("search/posts/{postId:\\d+}")
@Slf4j
public class PostController {

    private final PostsClient postsClient;

    private final FavouritePostsClient favouritePostsClient;

    private final PostReviewsClient postReviewsClient;

    @ModelAttribute(name = "post", binding = false)
    public Mono<Post> loadPost(@PathVariable("postId") Integer id) {
        return this.postsClient.findPost(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("search.posts.error.not_found")));
    }

    @GetMapping
    public Mono<String> getPostPage(@PathVariable("postId") int id, Model model) {
        model.addAttribute("inFavourite", false);
        return this.postReviewsClient.findPostReviewsByPostId(id)
                .collectList()
                .doOnNext(postReviews -> model.addAttribute("reviews", postReviews))
                .then(this.favouritePostsClient.findFavouritePostByPostId(id)
                        .doOnNext(favouritePost -> model.addAttribute("inFavourite", true)))
                .thenReturn("author/posts/post");
    }

    @PostMapping("add-to-favourites")
    public Mono<String> addPostToFavourites(@ModelAttribute("post") Mono<Post> postMono) {
        return postMono
                .map(Post::id)
                .flatMap(postId -> this.favouritePostsClient.addPostToFavourites(postId)
                        .thenReturn("redirect:/author/posts/%d".formatted(postId))
                        .onErrorResume(exception -> {
                            log.error(exception.getMessage(), exception);
                            return Mono.just("redirect:/author/posts/%d".formatted(postId));
                        }));
    }

    @PostMapping("remove-from-favourites")
    public Mono<String> removePostFromFavourites(@ModelAttribute("post") Mono<Post> postMono) {
        return postMono.map(Post::id)
                .flatMap(postId -> this.favouritePostsClient.removePostFromFavourites(postId)
                        .thenReturn("redirect:/author/posts/%d".formatted(postId)));
    }

    @PostMapping("create-review")
    public Mono<String> createReview(@PathVariable("postId") int id,
                                     NewPostReviewPayload payload,
                                     Model model) {
        return this.postReviewsClient.createPostReview(id, payload.rating(), payload.review())
                .thenReturn("redirect:/author/posts/%d".formatted(id))
                .onErrorResume(ClientBadRequestException.class, exception -> {
                    model.addAttribute("inFavourite", false);
                    model.addAttribute("payload", payload);
                    model.addAttribute("errors", exception.getErrors());
                    return this.favouritePostsClient.findFavouritePostByPostId(id)
                            .doOnNext(favouritePost -> model.addAttribute("inFavourite", true))
                            .thenReturn("author/posts/post");
                });
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "errors/404";
    }
}
