package com.egor.tasks.service;

import com.egor.tasks.controller.schema.dto.change.ChangeCommentsTextDataDto;
import com.egor.tasks.controller.schema.dto.input.CreateCommentsDto;
import com.egor.tasks.controller.schema.dto.output.OutputCommentsDto;
import com.egor.tasks.exception.CommentNotFound;
import com.egor.tasks.exception.ForbiddenChanges;
import com.egor.tasks.exception.TaskNotFound;
import com.egor.tasks.exception.UserNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentsService {
    void create(CreateCommentsDto comment, Long taskId, String email)
            throws UserNotFound, TaskNotFound;
    void edit(Long id, ChangeCommentsTextDataDto changes, String email)
            throws CommentNotFound, UserNotFound, ForbiddenChanges;
    void delete(Long id, String email) throws UserNotFound, ForbiddenChanges, CommentNotFound;
    OutputCommentsDto getComment(Long id) throws CommentNotFound;
    Page<OutputCommentsDto> getMultipleCommentsForUser(String email, Pageable pageable) throws UserNotFound;
    Page<OutputCommentsDto> getMultipleCommentsForTask(Long taskId, Pageable pageable) throws TaskNotFound;
}
