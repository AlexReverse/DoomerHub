package org.alexreverse.repository;

import org.alexreverse.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {

    @Query(value = "select p from Post p where p.title ilike :filter or p.description ilike :filter2")
    Flux<Post> findAllByTitleOrDescriptionLikeIgnoreCase(@Param("filter") String filter,
                                                         @Param("filter2") String filter2,
                                                         Pageable pageable);

    Flux<Post> findAllBy(Pageable pageable);

    Flux<Post> findAllByUserIdOrderByPostDate(String userId, Pageable pageable);
}
