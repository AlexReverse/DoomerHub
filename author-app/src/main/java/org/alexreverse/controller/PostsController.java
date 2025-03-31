package org.alexreverse.controller;

import lombok.RequiredArgsConstructor;
import org.alexreverse.client.FavouritePostsClient;
import org.alexreverse.client.PostsClient;
import org.alexreverse.controller.payload.NewPostPayload;
import org.alexreverse.entity.FavouritePost;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("search/posts")
public class PostsController {

    private final PostsClient postsClient;

    private final FavouritePostsClient favouritePostsService;

    @GetMapping("list")
    public Mono<String> getPostsListPage(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filter", filter);
        return this.postsClient.findAllPosts(filter)
                .collectList()
                .doOnNext(posts -> model.addAttribute("posts", posts))
                .thenReturn("search/posts/list");
    }

    @GetMapping("create")
    public Mono<String> getNewPostPage() {
        return Mono.just("search/posts/new_post");
    }

    @PostMapping("create")
    public Mono<String> createPost(NewPostPayload payload, Model model, OAuth2AuthenticationToken token) {
        try {
            return this.postsClient.createPost(payload.title(), payload.description(),
                            token.getPrincipal().getAttribute("sub"))
                    .thenReturn("redirect:/search/posts/list");
        } catch (Exception exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getMessage());
            return Mono.just("search/posts/new_post");
        }
    }

    @GetMapping("favourites")
    public Mono<String> getFavouritePostsPage(Model model,
                                                 @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filter", filter);
        return this.favouritePostsService.findFavouritePosts()
                .map(FavouritePost::postId)
                .collectList()
                .flatMap(favouritePosts -> this.postsClient.findAllPosts(filter)
                        .filter(post -> favouritePosts.contains(post.id()))
                        .collectList()
                        .doOnNext(posts -> model.addAttribute("posts", posts)))
                .thenReturn("search/posts/favourites");
    }

    @ModelAttribute
    public Mono<CsrfToken> loadCsrfToken(ServerWebExchange exchange) {
        return exchange.<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName())
                .doOnSuccess(token -> exchange.getAttributes()
                        .put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, token));
    }
}
