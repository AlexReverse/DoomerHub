package hub.doomer.search.controller;

import hub.doomer.search.controller.payload.NewPostPayload;
import hub.doomer.search.entity.Post;
import hub.doomer.search.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("search-api/posts")
public class PostsRestController {
    private final PostService postService;

    @GetMapping
    public Iterable<Post> findPosts(@RequestParam(name = "filter", required = false) String filter) {
        return this.postService.findAllPosts(filter);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody NewPostPayload payload,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder)
            throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Post post = this.postService.createPost(payload.title(), payload.description());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/search-api/posts/{postId}")
                            .build(Map.of("postId", post.getId())))
                    .body(post);
        }
    }
}
