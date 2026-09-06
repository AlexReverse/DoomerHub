package org.alexreverse.repository;

import org.alexreverse.entity.WorkExperience;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface WorkExperienceRepository extends ReactiveCrudRepository<WorkExperience, Long> {
}
