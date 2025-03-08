package org.alexreverse.repository;

import org.alexreverse.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PostRepository extends ReactiveCrudRepository<Post, Integer> {

    @Query(value = "select p from Post p where p.title ilike :filter")
    Flux<Post> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);
}
