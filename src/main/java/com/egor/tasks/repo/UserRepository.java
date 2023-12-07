package com.egor.tasks.repo;

import com.egor.tasks.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    // findById is default

    Optional<User> findByEmail(String email);
}