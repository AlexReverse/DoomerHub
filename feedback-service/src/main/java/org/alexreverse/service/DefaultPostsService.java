package org.alexreverse.service;


import lombok.RequiredArgsConstructor;
import org.alexreverse.entity.Post;
import org.alexreverse.repository.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DefaultPostsService implements PostsService {

    private final PostRepository postRepository;

    @Override
    public Flux<Post> findAllPosts(String filter, Pageable pageable) {
        if (filter != null && !filter.isBlank()) {
            return this.postRepository.findAllByTitleOrDescriptionLikeIgnoreCase("%" + filter + "%",
                    "%" + filter + "%", pageable);
        } else {
            return this.postRepository.findAllBy(pageable);
        }
    }

    @Override
    public Mono<Post> createPost(String title, String description, String userName) {
        return this.postRepository.save(new Post(null, title, description, userName, LocalDateTime.now()));
    }

    @Override
    public Flux<Post> findAllPostsByUser(String userId, Pageable pageable) {
        return this.postRepository.findAllByUserIdOrderByPostDate(userId, pageable);
    }
}
