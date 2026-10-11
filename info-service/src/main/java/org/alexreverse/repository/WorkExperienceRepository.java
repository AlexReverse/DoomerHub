package org.alexreverse.repository;

import org.alexreverse.entity.WorkExperience;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@EnableR2dbcRepositories
public interface WorkExperienceRepository extends ReactiveCrudRepository<WorkExperience, Long> {

    Mono<Void> deleteAllByUserId(UUID userId);

    Flux<WorkExperience> findAllByUserId(UUID userId);

    Mono<Void> deleteByIdAndUserId(Long id, UUID userId);
}
