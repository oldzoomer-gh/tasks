package com.egor.tasks.controller;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.controller.schema.dto.change.ChangeTaskTextDataDto;
import com.egor.tasks.controller.schema.dto.input.CreateTaskDto;
import com.egor.tasks.controller.schema.dto.output.OutputTaskDto;
import com.egor.tasks.exception.ForbiddenChanges;
import com.egor.tasks.exception.PaginationOutOfRange;
import com.egor.tasks.exception.TaskNotFound;
import com.egor.tasks.exception.UserNotFound;
import com.egor.tasks.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public void createTask(@RequestBody CreateTaskDto taskDto,
                           @RequestParam String assignedEmail,
                           Authentication authentication) throws UserNotFound {
        String authorEmail = authentication.getName();

        taskService.create(taskDto, authorEmail, assignedEmail);
    }

    @DeleteMapping("/delete")
    public void deleteTask(@RequestParam Long id,
                           Authentication authentication)
            throws UserNotFound, ForbiddenChanges, TaskNotFound {
        String authorEmail = authentication.getName();

        taskService.delete(id, authorEmail);
    }

    @PutMapping("/editStatus")
    public void editStatus(@RequestParam Long id,
                           @RequestParam TaskStatus status,
                           Authentication authentication)
            throws UserNotFound, ForbiddenChanges, TaskNotFound {
        String authorEmail = authentication.getName();

        taskService.editStatus(id, status, authorEmail);
    }

    @PutMapping("/editPriority")
    public void editPriority(@RequestParam Long id,
                             @RequestParam TaskPriority priority,
                             Authentication authentication)
            throws UserNotFound, ForbiddenChanges, TaskNotFound {
        String authorEmail = authentication.getName();

        taskService.editPriority(id, priority, authorEmail);
    }

    @PutMapping("/editNameAndDescription")
    public void editNameAndDescription(@RequestParam Long id,
                                       @RequestBody ChangeTaskTextDataDto textDataDto,
                                       Authentication authentication)
            throws UserNotFound, ForbiddenChanges, TaskNotFound {
        String authorEmail = authentication.getName();

        taskService.editNameAndDescription(id, textDataDto, authorEmail);
    }

    @PutMapping("/editAssignedUser")
    public void editAssignedUser(@RequestParam Long id,
                             @RequestParam String assignedEmail,
                             Authentication authentication)
            throws UserNotFound, ForbiddenChanges, TaskNotFound {
        String authorEmail = authentication.getName();

        taskService.editAssignedUser(id, assignedEmail, authorEmail);
    }

    @GetMapping("/getTask")
    public OutputTaskDto getTask(@RequestParam Long id) throws TaskNotFound {
        return taskService.getTask(id);
    }

    @GetMapping("/getAllTasksForUser")
    public Page<OutputTaskDto> getAllTasksForUser(@RequestParam int start,
                                                  @RequestParam int end,
                                                  @RequestParam String email)
            throws UserNotFound, PaginationOutOfRange {
        if ((end - start) < 1) {
            throw new PaginationOutOfRange("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);

        return taskService.getMultipleTasksForUser(email, pageable);
    }
}
