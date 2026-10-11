package org.alexreverse.repository;

import org.alexreverse.entity.AuthorInformation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@EnableR2dbcRepositories
public interface AuthorInformationRepository extends ReactiveCrudRepository<AuthorInformation, UUID> {
    @Query(value = "select mp from MainPage mp where mp.nickname ilike :filter or mp.name ilike :filter or mp.sur_name :filter")
    Flux<AuthorInformation> findAllByName(@Param("filter") String filter);

    Mono<Void> deleteByUserId(@Param("userId") UUID userId);
}
