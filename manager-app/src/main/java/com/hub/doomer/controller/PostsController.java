package com.hub.doomer.controller;

import com.hub.doomer.client.BadRequestException;
import com.hub.doomer.client.PostsRestClient;
import com.hub.doomer.controller.payload.NewPostPayload;
import com.hub.doomer.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("search/posts")
public class PostsController {

    private final PostsRestClient postsRestClient;

    @GetMapping(value = "list")
    public String getPostsList(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("posts", this.postsRestClient.findAllPosts(filter));
        model.addAttribute("filter", filter);
        return "search/posts/list";
    }

    @GetMapping("create")
    public String getNewPostPage() {
        return "search/posts/new_post";
    }

    @PostMapping("create")
    public String createPost(NewPostPayload payload, Model model) {
        try {
            Post post = this.postsRestClient.createPost(payload.title(), payload.description());
            return "redirect:/search/posts/%d".formatted(post.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "search/posts/new_post";
        }
    }
}
