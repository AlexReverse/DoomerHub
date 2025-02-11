package com.hub.doomer.repository;

import com.hub.doomer.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

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
}
