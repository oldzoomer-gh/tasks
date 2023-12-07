package com.egor.tasks.service;

import com.egor.tasks.dto.CommentsDto;
import com.egor.tasks.exception.UserNotFound;
import org.springframework.data.domain.Page;

public interface CommentsService {
    void create(CommentsDto comment);
    void edit(Long id);
    void edit(Long id, CommentsDto changes);
    CommentsDto getComment(Long id);
    Page<CommentsDto> getMultipleCommentsForCurrentUser();
    Page<CommentsDto> getMultipleCommentsForAnotherUser(String email) throws UserNotFound;
}
