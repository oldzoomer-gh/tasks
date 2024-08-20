package ru.gavrilovegor519.tasks.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gavrilovegor519.tasks.entity.Comments;

/**
 * Comments manipulation service layer
 * @author Egor Gavrilov (gavrilovegor519@gmail.com)
 */
public interface CommentsService {
    /**
     * Create new comment
     * @param comment Comment to create. Must contain text.
     * @param taskId ID of task.
     * @param email Email of author.
     */
    void create(Comments comment, Long taskId, String email);

    /**
     * Change text of comment
     * @param id ID of comment.
     * @param changes Changes to apply.
     * @param email Email of author.
     */
    void edit(Long id, Comments changes, String email);

    /**
     * Delete comment
     * @param id ID of comment.
     * @param email Email of author.
     */
    void delete(Long id, String email);

    /**
     * Get a comment
     * @param id ID of comment.
     * @return Comment.
     */
    Comments getComment(Long id);

    /**
     * Get all comments for user
     * @param email Email of author.
     * @param pageable Pageable object.
     * @return Page of comments.
     */
    Page<Comments> getMultipleCommentsForUser(String email, Pageable pageable);

    /**
     * Get all comments for task
     * @param taskId ID of task.
     * @param pageable Pageable object.
     * @return Page of comments.
     */
    Page<Comments> getMultipleCommentsForTask(Long taskId, Pageable pageable);
}
