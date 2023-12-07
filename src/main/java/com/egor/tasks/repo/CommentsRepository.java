package com.egor.tasks.repo;

import com.egor.tasks.entity.Comments;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentsRepository extends PagingAndSortingRepository<Comments, Long> {
    // findById is default
}