package com.egor.tasks.controller;

import com.egor.tasks.dto.CommentDto;
import com.egor.tasks.exception.PaginationOutOfRange;
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

    @PostMapping("/{taskId}/create")
    public void createComment(@RequestBody @Valid CommentDto comment,
                              @PathVariable Long taskId,
                              Authentication authentication) {
        String authorEmail = authentication.getName();

        commentsService.create(comment, taskId, authorEmail);
    }

    @PutMapping("/{id}/edit")
    public void editComment(@RequestBody @Valid CommentDto changes,
                            @PathVariable Long id,
                            Authentication authentication) {
        String authorEmail = authentication.getName();

        commentsService.edit(id, changes, authorEmail);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteComment(@PathVariable Long id,
                           Authentication authentication) {
        String authorEmail = authentication.getName();

        commentsService.delete(id, authorEmail);
    }

    @GetMapping("/{id}/getComment")
    public CommentDto getComment(@PathVariable Long id) {
        return commentsService.getComment(id);
    }

    @GetMapping("/getAllCommentsForUser")
    public Page<CommentDto> getAllTasksForUser(@RequestParam int start,
                                                  @RequestParam int end,
                                                  @RequestParam String email) {
        if ((end - start) < 1) {
            throw new PaginationOutOfRange("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);

        return commentsService.getMultipleCommentsForUser(email, pageable);
    }

    @GetMapping("/{taskId}/getAllCommentsForTask")
    public Page<CommentDto> getAllTasksForTask(@RequestParam int start,
                                                      @RequestParam int end,
                                                      @PathVariable long taskId) {
        if ((end - start) < 1) {
            throw new PaginationOutOfRange("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);

        return commentsService.getMultipleCommentsForTask(taskId, pageable);
    }
}
