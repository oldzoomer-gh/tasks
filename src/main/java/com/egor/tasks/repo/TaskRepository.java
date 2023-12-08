package com.egor.tasks.repo;

import com.egor.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends
        PagingAndSortingRepository<Task, Long>,
        JpaRepository<Task, Long> {
    // findById is default
}