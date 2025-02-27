package hub.doomer.search.service;

import hub.doomer.search.entity.Post;
import hub.doomer.search.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final PostRepository postRepository;

    @Override
    public Iterable<Post> findAllPosts(String filter) {
        if (filter != null && !filter.isBlank()) {
            return this.postRepository.findAllByTitleLikeIgnoreCase("%" + filter + "%");
        } else {
            return this.postRepository.findAll();

        }
    }

    @Override
    @Transactional
    public Post createPost(String title, String description) {
        return this.postRepository.save(new Post(null, title, description));
    }

    @Override
    public Optional<Post> findPost(Integer postId) {
        return this.postRepository.findById(postId);
    }

    @Override
    @Transactional
    public void updatePost(Integer id, String title, String description) {
        this.postRepository.findById(id)
                .ifPresentOrElse(post -> {
                    post.setTitle(title);
                    post.setDescription(description);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deletePost(Integer id) {
        this.postRepository.deleteById(id);
    }
}
