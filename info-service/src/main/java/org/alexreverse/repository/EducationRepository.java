package org.alexreverse.repository;

import org.alexreverse.entity.Education;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EducationRepository extends ReactiveCrudRepository<Education, Long> {
    Mono<Void> deleteByUserId(@Param("userId") UUID userId);

    Flux<Education> findAllByUserId(UUID userId);
}
