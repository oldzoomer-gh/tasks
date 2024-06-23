package com.egor.tasks.controller;

import com.egor.tasks.dto.input.comments.*;
import com.egor.tasks.dto.output.comments.CommentOutputDto;
import com.egor.tasks.entity.Comments;
import com.egor.tasks.exception.PaginationOutOfRangeException;
import com.egor.tasks.mapper.CommentMapper;
import com.egor.tasks.service.CommentsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0/comments")
@PreAuthorize("isAuthenticated()")
@AllArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;
    private final CommentMapper commentMapper;

    @PostMapping("/create")
    public void createComment(@RequestBody @Valid CreateCommentDto comment,
                              Authentication authentication) {
        String authorEmail = authentication.getName();
        Comments comment1 = commentMapper.map(comment);

        commentsService.create(comment1, comment.getTaskId(), authorEmail);
    }

    @PutMapping("/edit")
    public void editComment(@RequestBody @Valid EditCommentDto changes,
                            Authentication authentication) {
        String authorEmail = authentication.getName();
        Comments changes1 = commentMapper.map(changes);

        commentsService.edit(changes.getCommentId(), changes1, authorEmail);
    }

    @DeleteMapping("/delete")
    public void deleteComment(@RequestBody DeleteCommentDto deleteCommentDto,
                           Authentication authentication) {
        String authorEmail = authentication.getName();

        commentsService.delete(deleteCommentDto.getCommentId(), authorEmail);
    }

    @GetMapping("/get")
    public CommentOutputDto getComment(@RequestBody GetCommentDto getCommentDto) {
        Comments comment = commentsService.getComment(getCommentDto.getCommentId());
        return commentMapper.map(comment);
    }

    @GetMapping("/get/user")
    public Page<CommentOutputDto> getAllCommentsForUser(@RequestBody GetAllCommentsForUserDto getAllCommentsForUserDto) {
        Integer start = getAllCommentsForUserDto.getStart();
        Integer end = getAllCommentsForUserDto.getEnd();
        String email = getAllCommentsForUserDto.getEmail();

        if ((end - start) < 1) {
            throw new PaginationOutOfRangeException("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);
        Page<Comments> multipleCommentsForUser = commentsService.getMultipleCommentsForUser(email, pageable);

        return multipleCommentsForUser.map(commentMapper::map);
    }

    @GetMapping("/get/task")
    public Page<CommentOutputDto> getAllCommentsForTask(@RequestBody GetAllCommentsForTaskDto getAllCommentsForTaskDto) {
        Integer start = getAllCommentsForTaskDto.getStart();
        Integer end = getAllCommentsForTaskDto.getEnd();
        Long taskId = getAllCommentsForTaskDto.getTaskId();

        if ((end - start) < 1) {
            throw new PaginationOutOfRangeException("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);
        Page<Comments> multipleCommentsForTask = commentsService.getMultipleCommentsForTask(taskId, pageable);

        return multipleCommentsForTask.map(commentMapper::map);
    }
}
