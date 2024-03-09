package com.egor.tasks.service;

import com.egor.tasks.dto.change.ChangeCommentsTextDataDto;
import com.egor.tasks.dto.input.CreateCommentsDto;
import com.egor.tasks.dto.output.OutputCommentsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Comments manipulation service layer
 * @author Egor Gavrilov (gavrilovegor519@gmail.com)
 */
public interface CommentsService {
    /**
     * Create new comment
     * @param comment Comment to create. Must contain text.
     * @param taskId Id of task.
     * @param email Email of author.
     */
    void create(CreateCommentsDto comment, Long taskId, String email);

    /**
     * Change text of comment
     * @param id Id of comment.
     * @param changes Changes to apply.
     * @param email Email of author.
     */
    void edit(Long id, ChangeCommentsTextDataDto changes, String email);

    /**
     * Delete comment
     * @param id Id of comment.
     * @param email Email of author.
     */
    void delete(Long id, String email);

    /**
     * Get a comment
     * @param id Id of comment.
     * @return Comment.
     */
    OutputCommentsDto getComment(Long id);

    /**
     * Get all comments for user
     * @param email Email of author.
     * @param pageable Pageable object.
     * @return Page of comments.
     */
    Page<OutputCommentsDto> getMultipleCommentsForUser(String email, Pageable pageable);

    /**
     * Get all comments for task
     * @param taskId Id of task.
     * @param pageable Pageable object.
     * @return Page of comments.
     */
    Page<OutputCommentsDto> getMultipleCommentsForTask(Long taskId, Pageable pageable);
}
