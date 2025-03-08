package org.alexreverse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.UpdatePostPayload;
import org.alexreverse.entity.Post;
import org.alexreverse.service.PostService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("search-api/posts/{postId:\\d+}")
public class PostRestController {

    private final PostService postService;
    private final MessageSource messageSource;

    @ModelAttribute(name = "post", binding = false)
    public Mono<Post> getPost(@PathVariable("postId") int id) {
        return this.postService.findPost(id).switchIfEmpty(Mono.error(new NoSuchElementException("search.posts.error.not_found")));
    }

    @GetMapping
    public Mono<Post> findPost(@ModelAttribute("post") Post post) {
        return postService.findPost(post.getId());
    }

    @PatchMapping
    public Mono<ResponseEntity<Void>> updatePost(@PathVariable("postId") int postId,
                                        @Valid @RequestBody UpdatePostPayload payload) throws BindException {

            return this.postService.findPost(postId).flatMap(p ->
                    postService.updatePost(postId, payload.title(), payload.description())
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                    )
                    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deletePost(@PathVariable("postId") int postId) {
        return this.postService.findPost(postId).flatMap(p ->
                        postService.deletePost(postId)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)));
    }
}
