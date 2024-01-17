package com.egor.tasks.dao.repo;

import com.egor.tasks.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Long> {
    // findById is default

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}