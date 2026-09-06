package org.alexreverse.repository;

import org.alexreverse.entity.Education;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EducationRepository extends ReactiveCrudRepository<Education, Long> {
}
