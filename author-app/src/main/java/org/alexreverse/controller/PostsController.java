package org.alexreverse.controller;

import lombok.RequiredArgsConstructor;
import org.alexreverse.client.FavouritePostsClient;
import org.alexreverse.client.PostsClient;
import org.alexreverse.entity.FavouritePost;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("author/posts")
public class PostsController {

    private final PostsClient postsClient;

    private final FavouritePostsClient favouritePostsService;

    @GetMapping("list")
    public Mono<String> getPostsListPage(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filter", filter);
        return this.postsClient.findAllPosts(filter)
                .collectList()
                .doOnNext(posts -> model.addAttribute("posts", posts))
                .thenReturn("author/posts/list");
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
                .thenReturn("author/posts/favourites");
    }
}
