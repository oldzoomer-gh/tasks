package com.egor.tasks.controller;

import com.egor.tasks.dto.change.ChangeCommentsTextDataDto;
import com.egor.tasks.dto.input.CreateCommentsDto;
import com.egor.tasks.dto.output.OutputCommentsDto;
import com.egor.tasks.exception.PaginationOutOfRange;
import com.egor.tasks.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0/comments")
@AllArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping("/create")
    public void createComment(@RequestBody CreateCommentsDto comment,
                              @RequestParam Long taskId,
                              Authentication authentication) {
        String authorEmail = authentication.getName();

        commentsService.create(comment, taskId, authorEmail);
    }

    @PutMapping("/edit")
    public void editComment(@RequestBody ChangeCommentsTextDataDto changes,
                            @RequestParam Long id,
                            Authentication authentication) {
        String authorEmail = authentication.getName();

        commentsService.edit(id, changes, authorEmail);
    }

    @DeleteMapping("/delete")
    public void deleteComment(@RequestParam Long id,
                           Authentication authentication) {
        String authorEmail = authentication.getName();

        commentsService.delete(id, authorEmail);
    }

    @GetMapping("/getComment")
    public OutputCommentsDto getComment(@RequestParam Long id) {
        return commentsService.getComment(id);
    }

    @GetMapping("/getAllCommentsForUser")
    public Page<OutputCommentsDto> getAllTasksForUser(@RequestParam int start,
                                                  @RequestParam int end,
                                                  @RequestParam String email) {
        if ((end - start) < 1) {
            throw new PaginationOutOfRange("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);

        return commentsService.getMultipleCommentsForUser(email, pageable);
    }

    @GetMapping("/getAllCommentsForTask")
    public Page<OutputCommentsDto> getAllTasksForTask(@RequestParam int start,
                                                      @RequestParam int end,
                                                      @RequestParam long taskId) {
        if ((end - start) < 1) {
            throw new PaginationOutOfRange("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);

        return commentsService.getMultipleCommentsForTask(taskId, pageable);
    }
}
