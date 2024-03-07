package com.egor.tasks.service;

import com.egor.tasks.dto.change.ChangeCommentsTextDataDto;
import com.egor.tasks.dto.input.CreateCommentsDto;
import com.egor.tasks.dto.output.OutputCommentsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentsService {
    void create(CreateCommentsDto comment, Long taskId, String email);
    void edit(Long id, ChangeCommentsTextDataDto changes, String email);
    void delete(Long id, String email);
    OutputCommentsDto getComment(Long id);
    Page<OutputCommentsDto> getMultipleCommentsForUser(String email, Pageable pageable);
    Page<OutputCommentsDto> getMultipleCommentsForTask(Long taskId, Pageable pageable);
}
