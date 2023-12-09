package com.egor.tasks.repo;

import com.egor.tasks.entity.Comments;
import com.egor.tasks.entity.Task;
import com.egor.tasks.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentsRepository extends
        PagingAndSortingRepository<Comments, Long>,
        JpaRepository<Comments, Long> {
    // findById is default

    Page<Comments> findAllByAuthor(User author,
                               Pageable pageable);

    Page<Comments> findAllByTask(Task task,
                                 Pageable pageable);
}