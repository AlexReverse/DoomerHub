package com.hub.doomer.repository;

import com.hub.doomer.entity.Post;

import java.util.List;

public interface PostRepository {
    List<Post> findAll();

    Post save(Post post);
}
