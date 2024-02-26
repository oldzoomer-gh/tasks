package com.egor.tasks.controller;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.dto.change.ChangeTaskTextDataDto;
import com.egor.tasks.dto.input.CreateTaskDto;
import com.egor.tasks.dto.output.OutputTaskDto;
import com.egor.tasks.exception.PaginationOutOfRange;
import com.egor.tasks.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public void createTask(@RequestBody CreateTaskDto taskDto,
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
                                       @RequestBody ChangeTaskTextDataDto textDataDto,
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
    public OutputTaskDto getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @GetMapping("/getAllTasksForUser")
    public Page<OutputTaskDto> getAllTasksForUser(@RequestParam int start,
                                                  @RequestParam int end,
                                                  @RequestParam String email) {
        if ((end - start) < 1) {
            throw new PaginationOutOfRange("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);

        return taskService.getMultipleTasksForUser(email, pageable);
    }
}
