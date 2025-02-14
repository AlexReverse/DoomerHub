package com.hub.doomer.controller;

import com.hub.doomer.controller.payload.UpdatePostPayload;

import com.hub.doomer.entity.Post;
import com.hub.doomer.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping(("search/posts/{postId:\\d+}"))
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final MessageSource messageSource;
    @ModelAttribute("post")
    public Post post(@PathVariable("postId") int postId){
        return this.postService.findPost(postId)
                .orElseThrow(() -> new NoSuchElementException("search.errors.post.not_found"));
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
    public String updatePost(@ModelAttribute(name = "post", binding = false) Post post,
                             @Valid UpdatePostPayload updatePostPayload, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("payload", updatePostPayload);
            model.addAttribute("errors", result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "search/posts/edit";
        } else {
            this.postService.updatePost(post.getId(), updatePostPayload.title(), updatePostPayload.description());
            return "redirect:/search/posts/%d".formatted(post.getId());
        }
    }

    @PostMapping("delete")
    public String deletePost(@ModelAttribute("post") Post post) {
        this.postService.deletePost(post.getId());
        return "redirect:/search/posts/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }
}
