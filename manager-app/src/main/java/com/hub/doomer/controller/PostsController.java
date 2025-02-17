package com.hub.doomer.controller;

import com.hub.doomer.client.PostsRestClient;
import com.hub.doomer.controller.payload.NewPostPayload;
import com.hub.doomer.entity.Post;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
    public String createPost(@Valid NewPostPayload payload, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "search/posts/new_post";
        } else {
            Post post = this.postsRestClient.createPost(payload.title(), payload.description());
            return "redirect:/search/posts/%d".formatted(post.id());
        }
    }
}
