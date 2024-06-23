package com.egor.tasks.controller;

import com.egor.tasks.dto.input.tasks.*;
import com.egor.tasks.dto.output.tasks.TaskOutputDto;
import com.egor.tasks.entity.Task;
import com.egor.tasks.exception.PaginationOutOfRangeException;
import com.egor.tasks.mapper.TaskMapper;
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
    private final TaskMapper taskMapper;

    @PostMapping("/create")
    public void createTask(@RequestBody @Valid CreateTaskDto createTaskDto,
                           Authentication authentication) {
        String authorEmail = authentication.getName();
        Task task1 = taskMapper.map(createTaskDto);

        taskService.create(task1, authorEmail, createTaskDto.getAssignedEmail());
    }

    @DeleteMapping("/{id}/delete")
    public void deleteTask(@PathVariable Long id,
                           Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.delete(id, authorEmail);
    }

    @PutMapping("/{id}/edit/status")
    public void editStatus(@RequestBody @Valid EditTaskStatusDto editTaskStatusDto,
                           Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.editStatus(editTaskStatusDto.getTaskId(),
                editTaskStatusDto.getStatus(), authorEmail);
    }

    @PutMapping("/{id}/edit/priority")
    public void editPriority(@RequestBody @Valid EditTaskPriorityDto editTaskPriorityDto,
                             Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.editPriority(editTaskPriorityDto.getTaskId(),
                editTaskPriorityDto.getPriority(), authorEmail);
    }

    @PutMapping("/{id}/edit/description")
    public void editNameAndDescription(@RequestBody @Valid EditTaskDto editTaskDto,
                                       Authentication authentication) {
        String authorEmail = authentication.getName();
        Task task = taskMapper.map(editTaskDto);

        taskService.editNameAndDescription(editTaskDto.getTaskId(), task, authorEmail);
    }

    @PutMapping("/{id}/edit/assigned")
    public void editAssignedUser(@RequestBody @Valid EditTaskAssignedUserDto editTaskAssignedUserDto,
                                 Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.editAssignedUser(editTaskAssignedUserDto.getTaskId(),
                editTaskAssignedUserDto.getAssignedEmail(), authorEmail);
    }

    @GetMapping("/get/{id}")
    public TaskOutputDto getTask(@PathVariable Long id) {
        Task task = taskService.getTask(id);
        return taskMapper.map(task);
    }

    @GetMapping("/get")
    public Page<TaskOutputDto> getAllTasksForUser(@RequestParam int start,
                                                  @RequestParam int end,
                                                  @RequestParam String email) {
        if ((end - start) < 1) {
            throw new PaginationOutOfRangeException("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);
        Page<Task> multipleTasksForUser = taskService.getMultipleTasksForUser(email, pageable);

        return multipleTasksForUser.map(taskMapper::map);
    }
}
