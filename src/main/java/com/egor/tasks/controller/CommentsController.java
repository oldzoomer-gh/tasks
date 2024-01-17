package com.egor.tasks.controller;

import com.egor.tasks.controller.schema.dto.change.ChangeCommentsTextDataDto;
import com.egor.tasks.controller.schema.dto.input.CreateCommentsDto;
import com.egor.tasks.controller.schema.dto.output.OutputCommentsDto;
import com.egor.tasks.exception.*;
import com.egor.tasks.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping("/create")
    public void createComment(@RequestBody CreateCommentsDto comment,
                              @RequestParam Long taskId,
                              Authentication authentication) throws UserNotFound, TaskNotFound {
        String authorEmail = authentication.getName();

        commentsService.create(comment, taskId, authorEmail);
    }

    @PutMapping("/edit")
    public void editComment(@RequestBody ChangeCommentsTextDataDto changes,
                            @RequestParam Long id,
                            Authentication authentication)
            throws UserNotFound, ForbiddenChanges, CommentNotFound {
        String authorEmail = authentication.getName();

        commentsService.edit(id, changes, authorEmail);
    }

    @DeleteMapping("/delete")
    public void deleteComment(@RequestParam Long id,
                           Authentication authentication)
            throws UserNotFound, ForbiddenChanges, CommentNotFound {
        String authorEmail = authentication.getName();

        commentsService.delete(id, authorEmail);
    }

    @GetMapping("/getComment")
    public OutputCommentsDto getComment(@RequestParam Long id) throws CommentNotFound {
        return commentsService.getComment(id);
    }

    @GetMapping("/getAllCommentsForUser")
    public Page<OutputCommentsDto> getAllTasksForUser(@RequestParam int start,
                                                  @RequestParam int end,
                                                  @RequestParam String email)
            throws UserNotFound, PaginationOutOfRange {
        if ((end - start) < 1) {
            throw new PaginationOutOfRange("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);

        return commentsService.getMultipleCommentsForUser(email, pageable);
    }

    @GetMapping("/getAllCommentsForTask")
    public Page<OutputCommentsDto> getAllTasksForTask(@RequestParam int start,
                                                      @RequestParam int end,
                                                      @RequestParam long taskId)
            throws PaginationOutOfRange, TaskNotFound {
        if ((end - start) < 1) {
            throw new PaginationOutOfRange("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);

        return commentsService.getMultipleCommentsForTask(taskId, pageable);
    }
}
