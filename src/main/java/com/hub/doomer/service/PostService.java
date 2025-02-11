package com.hub.doomer.service;

import com.hub.doomer.entity.Post;
import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAllPosts();

    Post createPost(String name, String description);

    Optional<Post> findPost(int postId);

    void updatePost(Integer id, String title, String description);

    void deletePost(Integer id);
}
