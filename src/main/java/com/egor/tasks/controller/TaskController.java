package com.egor.tasks.controller;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.dto.TaskDto;
import com.egor.tasks.exception.PaginationOutOfRangeException;
import com.egor.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0/tasks")
@PreAuthorize("isAuthenticated()")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public void createTask(@RequestBody @Valid TaskDto taskDto,
                           @RequestParam String assignedEmail,
                           Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.create(taskDto, authorEmail, assignedEmail);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteTask(@PathVariable Long id,
                           Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.delete(id, authorEmail);
    }

    @PutMapping("/{id}/editStatus")
    public void editStatus(@PathVariable Long id,
                           @RequestParam TaskStatus status,
                           Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.editStatus(id, status, authorEmail);
    }

    @PutMapping("/{id}/editPriority")
    public void editPriority(@PathVariable Long id,
                             @RequestParam TaskPriority priority,
                             Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.editPriority(id, priority, authorEmail);
    }

    @PutMapping("/{id}/editNameAndDescription")
    public void editNameAndDescription(@PathVariable Long id,
                                       @RequestBody @Valid TaskDto textDataDto,
                                       Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.editNameAndDescription(id, textDataDto, authorEmail);
    }

    @PutMapping("/{id}/editAssignedUser")
    public void editAssignedUser(@PathVariable Long id,
                             @RequestParam String assignedEmail,
                             Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.editAssignedUser(id, assignedEmail, authorEmail);
    }

    @GetMapping("/{id}/getTask")
    public TaskDto getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @GetMapping("/getAllTasksForUser")
    public Page<TaskDto> getAllTasksForUser(@RequestParam int start,
                                                  @RequestParam int end,
                                                  @RequestParam String email) {
        if ((end - start) < 1) {
            throw new PaginationOutOfRangeException("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);

        return taskService.getMultipleTasksForUser(email, pageable);
    }
}
