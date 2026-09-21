package org.alexreverse.service;


import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.NewPostPayload;
import org.alexreverse.dto.PostDto;
import org.alexreverse.dto.mapper.FeedbackMapper;
import org.alexreverse.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultPostsService implements PostsService {

    private final PostRepository postRepository;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public Flux<PostDto> findAllPosts(String filter, Pageable pageable) {
        if (filter != null && !filter.isBlank()) {
            return postRepository.findAllByTitleOrDescriptionLikeIgnoreCase("%" + filter + "%",
                    "%" + filter + "%", pageable).map(feedbackMapper::entityToDto);
        } else {
            return postRepository.findAllBy(pageable).map(feedbackMapper::entityToDto);
        }
    }

    @Override
    public Mono<PostDto> createPost(NewPostPayload payload, UUID userId) {
        return postRepository.save(feedbackMapper.createEntity(payload, userId, LocalDateTime.now()))
                .map(feedbackMapper::entityToDto);
    }

    @Override
    public Flux<PostDto> findAllPostsByUser(UUID userId, Pageable pageable) {
        return postRepository.findAllByUserIdOrderByPostDate(userId, pageable).map(feedbackMapper::entityToDto);
    }
}
