package com.egor.tasks.dao.repo;

import com.egor.tasks.dao.entity.Comments;
import com.egor.tasks.dao.entity.Task;
import com.egor.tasks.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends
        JpaRepository<Comments, Long> {
    // findById is default

    Page<Comments> findAllByAuthor(User author,
                               Pageable pageable);

    Page<Comments> findAllByTask(Task task,
                                 Pageable pageable);
}