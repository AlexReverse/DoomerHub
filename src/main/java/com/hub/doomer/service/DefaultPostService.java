package com.hub.doomer.service;

import com.hub.doomer.entity.Post;
import com.hub.doomer.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

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
}
