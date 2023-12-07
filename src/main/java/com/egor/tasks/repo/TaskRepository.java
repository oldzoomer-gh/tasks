package com.egor.tasks.repo;

import com.egor.tasks.entity.Task;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    // findById is default
}