package com.egor.tasks.repo;

import com.egor.tasks.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentsRepository extends
        PagingAndSortingRepository<Comments, Long>,
        JpaRepository<Comments, Long> {
    // findById is default
}