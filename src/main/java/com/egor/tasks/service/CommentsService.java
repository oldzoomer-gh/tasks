package com.egor.tasks.service;

import com.egor.tasks.dto.change.ChangeCommentsTextDataDto;
import com.egor.tasks.dto.input.CreateCommentsDto;
import com.egor.tasks.dto.output.OutputCommentsDto;
import com.egor.tasks.exception.CommentNotFound;
import com.egor.tasks.exception.UserNotFound;
import org.springframework.data.domain.Page;

public interface CommentsService {
    void create(CreateCommentsDto comment, Long taskId, String email);
    void edit(Long id, ChangeCommentsTextDataDto changes, String email) throws CommentNotFound;
    OutputCommentsDto getComment(Long id) throws CommentNotFound;
    Page<OutputCommentsDto> getMultipleCommentsForUser(String email) throws UserNotFound;
}
