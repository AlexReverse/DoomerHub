package org.alexreverse.repository;

import org.alexreverse.entity.AuthorUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AuthorUserRepository extends ReactiveCrudRepository<AuthorUser, Integer> {
    Mono<AuthorUser> findByUsername(String name);
}
