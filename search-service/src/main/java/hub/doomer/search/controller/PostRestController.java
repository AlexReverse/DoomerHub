package hub.doomer.search.controller;

import hub.doomer.search.controller.payload.UpdatePostPayload;
import hub.doomer.search.entity.Post;
import hub.doomer.search.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("search-api/posts/{postId:\\d+}")
public class PostRestController {

    private final PostService postService;
    private final MessageSource messageSource;

    @ModelAttribute("post")
    public Post getPost(@PathVariable("postId") int postId) {
        return this.postService.findPost(postId)
                .orElseThrow(() -> new NoSuchElementException("search.errors.post.not_found"));
    }

    @GetMapping
    public Post findPost(@ModelAttribute("post") Post post) {
        return post;
    }

    @PatchMapping
    public ResponseEntity<?> updatePost(@PathVariable("postId") int postId,
                                        @Valid @RequestBody UpdatePostPayload payload,
                                        BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.postService.updatePost(postId, payload.title(), payload.description());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePost(@PathVariable("postId") int postId) {
        this.postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)));
    }
}
