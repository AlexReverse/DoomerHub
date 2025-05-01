package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.Post;
import org.alexreverse.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final PostRepository postRepository;

    @Override
    public Flux<Post> findAllPosts(String filter) {
        if (filter != null && !filter.isBlank()) {
            return this.postRepository.findAllByTitleLikeIgnoreCase("%" + filter + "%");
        } else {
            return this.postRepository.findAll();
        }
    }

    @Override
    public Mono<Post> createPost(String title, String description, String userName, String englishTranslation) {
        return this.postRepository.save(new Post(null, title, description, userName, LocalDateTime.now(),
                englishTranslation));
    }

    @Override
    public Mono<Post> findPost(Long postId) {
        return this.postRepository.findById(postId);
    }

    @Override
    public Mono<Void> updatePost(Long id, String title, String description) {
        return this.postRepository.findById(id)
                .flatMap(post -> {
                    post.setTitle(title);
                    post.setDescription(description);
                    return postRepository.save(post);
                })
                .map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND)).then();
    }

    @Override
    public Mono<Void> deletePost(Long id) {
        return this.postRepository.deleteById(id);
    }
}
