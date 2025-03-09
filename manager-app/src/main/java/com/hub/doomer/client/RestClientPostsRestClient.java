package com.hub.doomer.client;

import com.hub.doomer.controller.payload.NewPostPayload;
import com.hub.doomer.controller.payload.UpdatePostPayload;
import com.hub.doomer.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class RestClientPostsRestClient implements PostsRestClient {

    private static final ParameterizedTypeReference<List<Post>> POSTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public List<Post> findAllPosts(String filter) {
        return this.restClient
                .get()
                .uri("/search-api/posts?filter={filter}", filter)
                .retrieve()
                .body(POSTS_TYPE_REFERENCE);
    }

    @Override
    public Post createPost(String title, String description) {
        try {
            return this.restClient
                    .post()
                    .uri("/search-api/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewPostPayload(title, description))
                    .retrieve()
                    .body(Post.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Post> findPost(int postId) {
        try {
            return Optional.ofNullable(this.restClient.get()
                    .uri("/search-api/posts/{postId}", postId)
                    .retrieve()
                    .body(Post.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updatePost(int postId, String title, String description) {
        try {
            this.restClient
                    .patch()
                    .uri("/search-api/posts/{postId}", postId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdatePostPayload(title, description))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deletePost(int postId) {
        try {
            this.restClient
                    .delete()
                    .uri("/search-api/posts/{postId}", postId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
