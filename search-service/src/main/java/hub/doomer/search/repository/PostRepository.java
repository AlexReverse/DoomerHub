package hub.doomer.search.repository;

import hub.doomer.search.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> findAll();

    Post save(Post post);

    Optional<Post> findById(Integer postId);

    void deleteById(Integer id);
}
