package com.hub.doomer.service;

import com.hub.doomer.entity.Post;
import java.util.List;

public interface PostService {
    List<Post> findAllPosts();

    Post createPost(String name, String description);
}
