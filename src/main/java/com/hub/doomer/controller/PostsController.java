package com.hub.doomer.controller;

import com.hub.doomer.controller.payload.NewPostPayload;
import com.hub.doomer.entity.Post;
import com.hub.doomer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("search/posts")
public class PostsController {

    private final PostService postService;

    @GetMapping(value = "list")
    public String getPostsList(Model model) {
        model.addAttribute("posts", this.postService.findAllPosts());
        return "search/posts/list";
    }

    @GetMapping("create")
    public String getNewPostPage() {
        return "search/posts/new_post";
    }

    @PostMapping("create")
    public String createPost(NewPostPayload payload) {
        Post post = this.postService.createPost(payload.title(), payload.description());
        return "redirect:/search/posts/%d".formatted(post.getId());
    }
}
