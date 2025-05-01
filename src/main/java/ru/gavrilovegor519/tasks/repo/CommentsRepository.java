package ru.gavrilovegor519.tasks.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.gavrilovegor519.tasks.entity.Comments;
import ru.gavrilovegor519.tasks.entity.Task;
import ru.gavrilovegor519.tasks.entity.User;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Page<Comments> findAllByAuthor(User author, Pageable pageable);

    Page<Comments> findAllByTask(Task task, Pageable pageable);
}