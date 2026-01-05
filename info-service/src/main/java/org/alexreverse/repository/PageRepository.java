package org.alexreverse.repository;

import org.alexreverse.entity.MainPage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PageRepository extends ReactiveCrudRepository<MainPage, UUID> {
    @Query(value = "select mp from MainPage mp where mp.nickname ilike :filter or mp.name ilike :filter or mp.sur_name :filter")
    Flux<MainPage> findAllByName(@Param("filter") String filter);

    Mono<Void> deleteByUserId(@Param("userId") UUID userId);
}
