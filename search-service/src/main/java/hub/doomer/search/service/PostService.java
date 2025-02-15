package hub.doomer.search.service;


import hub.doomer.search.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAllPosts();

    Post createPost(String title, String description);

    Optional<Post> findPost(int postId);

    void updatePost(Integer id, String title, String description);

    void deletePost(Integer id);
}
