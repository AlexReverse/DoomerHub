package hub.doomer.search.repository;


import hub.doomer.search.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryPostRepository implements PostRepository {

    private final List<Post> postsList = Collections.synchronizedList(new LinkedList<>());


    @Override
    public List<Post> findAll() {
        return Collections.unmodifiableList(this.postsList);
    }

    @Override
    public Post save(Post post) {
        post.setId(this.postsList.stream()
                .max(Comparator.comparingInt(Post::getId))
                .map(Post::getId)
                .orElse(0) + 1);
        this.postsList.add(post);
        return post;

    }

    @Override
    public Optional<Post> findById(Integer postId) {
        return this.postsList.stream()
                .filter(post -> Objects.equals(postId, post.getId()))
                .findFirst();
    }

    @Override
    public void deleteById(Integer id) {
        this.postsList.removeIf(post -> Objects.equals(id, post.getId()));
    }
}
