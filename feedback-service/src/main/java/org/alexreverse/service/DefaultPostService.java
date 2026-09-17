package org.alexreverse.service;

import lombok.RequiredArgsConstructor;
import org.alexreverse.controller.payload.UpdatePostPayload;
import org.alexreverse.dto.PostDto;
import org.alexreverse.dto.mapper.FeedbackMapper;
import org.alexreverse.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final PostRepository postRepository;

    private FeedbackMapper feedbackMapper;

    @Override
    public Mono<PostDto> findPost(Long postId) {
        return postRepository
                .findById(postId)
                .map(feedbackMapper::entityToDto);
    }

    @Override
    public Mono<Void> updatePost(Long id, UpdatePostPayload payload) {
        return postRepository.findById(id)
                .flatMap(post -> {
                    feedbackMapper.updateEntity(payload, post);
                    return postRepository.save(post);
                })
                .map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND)).then();
    }

    @Override
    public Mono<Void> deletePost(Long id) {
        return postRepository.deleteById(id);
    }
}
