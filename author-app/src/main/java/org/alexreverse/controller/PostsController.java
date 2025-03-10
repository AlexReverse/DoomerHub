package org.alexreverse.controller;

import lombok.RequiredArgsConstructor;
import org.alexreverse.client.FavouritePostsClient;
import org.alexreverse.client.PostsClient;
import org.alexreverse.controller.payload.NewPostPayload;
import org.alexreverse.entity.FavouritePost;
import org.alexreverse.entity.Post;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String getNewPostPage() {
        return "search/posts/new_post";
    }

    @PostMapping("create")
    public String createPost(NewPostPayload payload, Model model) {
        try {
            Mono<Post> post = this.postsClient.createPost(payload.title(), payload.description());
            return "redirect:/search/posts/%d".formatted(post.map(Post::id));
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getMessage());
            return "search/posts/new_post";
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
}
