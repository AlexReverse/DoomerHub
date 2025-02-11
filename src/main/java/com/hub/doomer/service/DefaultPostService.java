package com.hub.doomer.service;

import com.hub.doomer.entity.Post;
import com.hub.doomer.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final PostRepository postRepository;

    public List<Post> findAllPosts() {
        return this.postRepository.findAll();
    }

    @Override
    public Post createPost(String title, String description) {
        return this.postRepository.save(new Post(null, title, description));
    }

    @Override
    public Optional<Post> findPost(int postId) {
        return this.postRepository.findById(postId);
    }

    @Override
    public void updatePost(Integer id, String title, String description) {
        this.postRepository.findById(id)
                .ifPresentOrElse(post -> {
                    post.setTitle(title);
                    post.setDescription(description);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    public void deletePost(Integer id) {
        this.postRepository.deleteById(id);
    }
}
