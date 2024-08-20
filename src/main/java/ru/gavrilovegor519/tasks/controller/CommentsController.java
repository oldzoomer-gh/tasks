package ru.gavrilovegor519.tasks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.gavrilovegor519.tasks.dto.input.comments.CreateCommentDto;
import ru.gavrilovegor519.tasks.dto.input.comments.EditCommentDto;
import ru.gavrilovegor519.tasks.dto.output.comments.CommentOutputDto;
import ru.gavrilovegor519.tasks.entity.Comments;
import ru.gavrilovegor519.tasks.exception.PaginationOutOfRangeException;
import ru.gavrilovegor519.tasks.mapper.CommentMapper;
import ru.gavrilovegor519.tasks.service.CommentsService;

@RestController
@RequestMapping("/api/1.0/comments")
@PreAuthorize("isAuthenticated()")
@AllArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;
    private final CommentMapper commentMapper;

    @PostMapping("/create")
    @Operation(summary = "Create a comment")
    public void createComment(@Parameter(description = "Comment data", required = true)
                              @RequestBody @Valid CreateCommentDto comment,
                              Authentication authentication) {
        String authorEmail = authentication.getName();
        Comments comment1 = commentMapper.map(comment);

        commentsService.create(comment1, comment.getTaskId(), authorEmail);
    }

    @PutMapping("/edit")
    @Operation(summary = "Edit a comment")
    public void editComment(@Parameter(description = "Comment changes", required = true)
                            @RequestBody @Valid EditCommentDto changes,
                            Authentication authentication) {
        String authorEmail = authentication.getName();
        Comments changes1 = commentMapper.map(changes);

        commentsService.edit(changes.getCommentId(), changes1, authorEmail);
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete a comment")
    public void deleteComment(@Parameter(description = "ID of comment", required = true)
                              @PathVariable Long id,
                              Authentication authentication) {
        String authorEmail = authentication.getName();

        commentsService.delete(id, authorEmail);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get one comment by ID",
            responses = {
                    @ApiResponse(description = "The comment",
                            useReturnTypeSchema = true)
            })
    public CommentOutputDto getComment(@PathVariable
                                       @Parameter(description = "ID of comment", required = true)
                                       Long id) {
        Comments comment = commentsService.getComment(id);
        return commentMapper.map(comment);
    }

    @GetMapping("/get/user")
    public Page<CommentOutputDto> getAllCommentsForUser(@RequestParam int start,
                                                        @RequestParam int end,
                                                        @RequestParam String email) {
        if ((end - start) < 1) {
            throw new PaginationOutOfRangeException("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);
        Page<Comments> multipleCommentsForUser = commentsService.getMultipleCommentsForUser(email, pageable);

        return multipleCommentsForUser.map(commentMapper::map);
    }

    @GetMapping("/get/task")
    public Page<CommentOutputDto> getAllCommentsForTask(@RequestParam int start,
                                                        @RequestParam int end,
                                                        @PathVariable long taskId) {
        if ((end - start) < 1) {
            throw new PaginationOutOfRangeException("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);
        Page<Comments> multipleCommentsForTask = commentsService.getMultipleCommentsForTask(taskId, pageable);

        return multipleCommentsForTask.map(commentMapper::map);
    }
}
