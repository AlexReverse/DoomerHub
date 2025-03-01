package hub.doomer.search.service;


import hub.doomer.search.entity.Post;

import java.util.Optional;

public interface PostService {
    Iterable<Post> findAllPosts(String filter);

    Post createPost(String title, String description);

    Optional<Post> findPost(int postId);

    void updatePost(int id, String title, String description);

    void deletePost(Integer id);
}
