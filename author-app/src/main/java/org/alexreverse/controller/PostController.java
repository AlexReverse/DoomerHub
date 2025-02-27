package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.client.PostsClient;
import org.alexreverse.controller.payload.NewPostReviewPayload;
import org.alexreverse.entity.Post;
import org.alexreverse.service.FavouritePostsService;
import org.alexreverse.service.PostReviewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("author/posts/{postId:\\d+}")
public class PostController {

    private final PostsClient postsClient;

    private final FavouritePostsService favouritePostsService;

    private final PostReviewsService postReviewsService;

    @ModelAttribute(name = "post", binding = false)
    public Mono<Post> loadPost(@PathVariable("postId") Integer id) {
        return this.postsClient.findPost(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("author.posts.error.not_found")));
    }

    @GetMapping
    public Mono<String> getPostPage(@PathVariable("postId") Integer id, Model model) {
        model.addAttribute("inFavourite", false);
        return this.postReviewsService.findPostReviewsByPost(id)
                .collectList()
                .doOnNext(postReviews -> model.addAttribute("reviews", postReviews))
                .then(this.favouritePostsService.findFavouritePostByPost(id)
                    .doOnNext(favouritePost -> model.addAttribute("inFavourite", true)))
                .thenReturn("author/posts/post");
    }

    @PostMapping("add-to-favourites")
    public Mono<String> addPostToFavourites(@ModelAttribute("post") Mono<Post> postMono) {
        return postMono.map(Post::id)
                .flatMap(postId -> this.favouritePostsService.addPostToFavourites(postId)
                    .thenReturn("redirect:/author/posts/%d".formatted(postId)));
    }

    @PostMapping("remove-from-favourites")
    public Mono<String> removePostFromFavourites(@ModelAttribute("post") Mono<Post> postMono) {
        return postMono.map(Post::id)
                .flatMap(postId -> this.favouritePostsService.removePostFromFavourites(postId)
                        .thenReturn("redirect:/author/posts/%d".formatted(postId)));
    }

    @PostMapping("create-review")
    public Mono<String> createReview(@PathVariable("productId") int id,
                                     @Valid NewPostReviewPayload payload,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("inFavourite", false);
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return this.favouritePostsService.findFavouritePostByPost(id)
                    .doOnNext(favouriteProduct -> model.addAttribute("inFavourite", true))
                    .thenReturn("customer/products/product");
        } else {
            return this.postReviewsService.createPostReview(id, payload.rating(), payload.review())
                    .thenReturn("redirect:/customer/products/%d".formatted(id));
        }
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "errors/404";
    }
}
