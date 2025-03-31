package org.alexreverse.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexreverse.client.FavouritePostsClient;
import org.alexreverse.client.PostReviewsClient;
import org.alexreverse.client.PostsClient;
import org.alexreverse.client.exception.ClientBadRequestException;
import org.alexreverse.controller.payload.NewPostReviewPayload;
import org.alexreverse.controller.payload.UpdatePostPayload;
import org.alexreverse.entity.Post;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
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

    @ModelAttribute(name = "post")
    public Mono<Post> loadPost(@PathVariable("postId") int id) {
        return this.postsClient.findPost(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("search.posts.error.not_found")));
    }

    @GetMapping
    public Mono<String> getPost() {
        return Mono.just("search/posts/post");
    }

    @GetMapping("edit")
    public Mono<String> getPostEditPage() {
        return Mono.just("search/posts/edit");
    }

    @PostMapping("edit")
    public Mono<String> updatePost(@ModelAttribute(name = "post", binding = false) Post post,
                                   UpdatePostPayload payload, Model model, OAuth2AuthenticationToken token) {
        try {
            if (!post.userId().equals(token.getPrincipal().getAttribute("sub"))) {
                throw new AccessDeniedException("The user %s is not authorized or has no rights for post id = %d"
                        .formatted(token.getPrincipal().getAttribute("sub"), post.id()));
            }
            return this.postsClient.updatePost(post.id(), payload.title(), payload.description())
                    .thenReturn("redirect:/search/posts/%d".formatted(post.id()));
        } catch (AccessDeniedException exception) {
            log.info(exception.getMessage());
            return Mono.just("redirect:/search/posts/list");
        } catch (Exception exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getMessage());
            return Mono.just("search/posts/edit");
        }
    }

    @PostMapping("delete")
    public Mono<String> deletePost(@ModelAttribute("post") Post post, OAuth2AuthenticationToken token) {
        try {
            if (!post.userId().equals(token.getPrincipal().getAttribute("sub"))) {
                throw new AccessDeniedException("The user %s is not authorized or has no rights for post id = %d".formatted(
                        token.getPrincipal().getAttribute("sub"), post.id()));
            }
            return this.postsClient.deletePost(post.id())
                    .thenReturn("redirect:/search/posts/list");
        } catch (AccessDeniedException exception) {
            log.info(exception.getMessage());
            return Mono.just("redirect:/search/posts/list");
        }
    }

    @PostMapping("add-to-favourites")
    public Mono<String> addPostToFavourites(@ModelAttribute("post") Mono<Post> postMono) {
        return postMono
                .map(Post::id)
                .flatMap(postId -> this.favouritePostsClient.addPostToFavourites(postId)
                        .thenReturn("redirect:/search/posts/%d".formatted(postId))
                        .onErrorResume(exception -> {
                            log.error(exception.getMessage(), exception);
                            return Mono.just("redirect:/search/posts/%d".formatted(postId));
                        }));
    }

    @PostMapping("remove-from-favourites")
    public Mono<String> removePostFromFavourites(@ModelAttribute("post") Mono<Post> postMono) {
        return postMono.map(Post::id)
                .flatMap(postId -> this.favouritePostsClient.removePostFromFavourites(postId)
                        .thenReturn("redirect:/search/posts/%d".formatted(postId)));
    }

    @PostMapping("create-review")
    public Mono<String> createReview(@PathVariable("postId") int id,
                                     NewPostReviewPayload payload,
                                     Model model) {
        return this.postReviewsClient.createPostReview(id, payload.rating(), payload.review())
                .thenReturn("redirect:/search/posts/%d".formatted(id))
                .onErrorResume(ClientBadRequestException.class, exception -> {
                    model.addAttribute("inFavourite", false);
                    model.addAttribute("payload", payload);
                    model.addAttribute("errors", exception.getErrors());
                    return this.favouritePostsClient.findFavouritePostByPostId(id)
                            .doOnNext(favouritePost -> model.addAttribute("inFavourite", true))
                            .thenReturn("search/posts/post");
                });
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "errors/404";
    }

    @ModelAttribute
    public Mono<CsrfToken> loadCsrfToken(ServerWebExchange exchange) {
        return exchange.<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName())
                .doOnSuccess(token -> exchange.getAttributes()
                        .put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, token));
    }
}
