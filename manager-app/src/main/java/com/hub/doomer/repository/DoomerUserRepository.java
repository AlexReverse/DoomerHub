package com.hub.doomer.repository;

import com.hub.doomer.entity.DoomerUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DoomerUserRepository extends CrudRepository<DoomerUser, Integer> {

    Optional<DoomerUser> findByUsername(String username);
}
