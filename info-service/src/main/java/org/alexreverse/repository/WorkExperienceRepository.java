package org.alexreverse.repository;

import org.alexreverse.entity.WorkExperience;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface WorkExperienceRepository extends ReactiveCrudRepository<WorkExperience, Long> {
    Mono<Void> deleteByUserId(@Param("userId") UUID userId);

    Flux<WorkExperience> findAllByUserId(UUID userId);
}
