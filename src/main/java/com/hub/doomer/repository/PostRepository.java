package com.hub.doomer.repository;

import com.hub.doomer.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> findAll();

    Post save(Post post);

    Optional<Post> findById(Integer postId);

    void deleteById(Integer id);
}
