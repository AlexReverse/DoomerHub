package com.hub.doomer.client;

import com.hub.doomer.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostsRestClient {

    List<Post> findAllPosts(String filter);

    Post createPost(String title, String description);

    Optional<Post> findPost(int postId);

    void updatePost(int postId, String title, String description);

    void deletePost(int postId);
}
