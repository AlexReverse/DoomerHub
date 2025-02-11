package com.hub.doomer.controller;

import com.hub.doomer.controller.payload.UpdatePostPayload;
import com.hub.doomer.entity.Post;
import com.hub.doomer.service.PostService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("search/posts/{postId:\\d+}")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @ModelAttribute("post")
    public Post post(@PathVariable("postId") int postId){
        return this.postService.findPost(postId).orElseThrow();
    }
    @GetMapping
    public String getPost() {
        return "search/posts/post";
    }

    @GetMapping("edit")
    public String getPostEditPage() {
        return "search/posts/edit";
    }

    @PostMapping("edit")
    public String updatePost(@ModelAttribute("post") Post post, UpdatePostPayload updatePostPayload) {
        this.postService.updatePost(post.getId(), updatePostPayload.title(), updatePostPayload.description());
        return "redirect:/search/posts/%d".formatted(post.getId());
    }

    @PostMapping("delete")
    public String deletePost(@ModelAttribute("post") Post post) {
        this.postService.deletePost(post.getId());
        return "redirect:/search/posts/list";
    }
}
