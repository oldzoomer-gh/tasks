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
import ru.gavrilovegor519.tasks.dto.input.tasks.*;
import ru.gavrilovegor519.tasks.dto.output.tasks.TaskOutputDto;
import ru.gavrilovegor519.tasks.entity.Task;
import ru.gavrilovegor519.tasks.exception.PaginationOutOfRangeException;
import ru.gavrilovegor519.tasks.mapper.TaskMapper;
import ru.gavrilovegor519.tasks.service.TaskService;

@RestController
@RequestMapping("/api/1.0/tasks")
@PreAuthorize("isAuthenticated()")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping("/create")
    @Operation(summary = "Create a task")
    public void createTask(@Parameter(description = "Task data", required = true)
                           @RequestBody @Valid CreateTaskDto createTaskDto,
                           Authentication authentication) {
        String authorEmail = authentication.getName();
        Task task1 = taskMapper.map(createTaskDto);

        taskService.create(task1, authorEmail, createTaskDto.getAssignedEmail());
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete a task")
    public void deleteTask(@Parameter(description = "Task ID", required = true)
                           @PathVariable Long id,
                           Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.delete(id, authorEmail);
    }

    @PutMapping("/{id}/edit/status")
    @Operation(summary = "Edit task status")
    public void editStatus(@Parameter(description = "Status edit data", required = true)
                           @RequestBody @Valid EditTaskStatusDto editTaskStatusDto,
                           @PathVariable Long id,
                           Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.editStatus(id, editTaskStatusDto.getStatus(), authorEmail);
    }

    @PutMapping("/{id}/edit/priority")
    @Operation(summary = "Edit task priority")
    public void editPriority(@Parameter(description = "Priority edit data", required = true)
                             @RequestBody @Valid EditTaskPriorityDto editTaskPriorityDto,
                             @PathVariable Long id,
                             Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.editPriority(id, editTaskPriorityDto.getPriority(), authorEmail);
    }

    @PutMapping("/{id}/edit/description")
    @Operation(summary = "Edit task description")
    public void editNameAndDescription(@Parameter(description = "Description edit data", required = true)
                                       @RequestBody @Valid EditTaskDto editTaskDto,
                                       @PathVariable Long id,
                                       Authentication authentication) {
        String authorEmail = authentication.getName();
        Task task = taskMapper.map(editTaskDto);

        taskService.editNameAndDescription(id, task, authorEmail);
    }

    @PutMapping("/{id}/edit/assigned")
    @Operation(summary = "Edit assigned user of the task")
    public void editAssignedUser(@Parameter(description = "Assigned user edit data", required = true)
                                 @RequestBody @Valid EditTaskAssignedUserDto editTaskAssignedUserDto,
                                 @PathVariable Long id,
                                 Authentication authentication) {
        String authorEmail = authentication.getName();

        taskService.editAssignedUser(id, editTaskAssignedUserDto.getAssignedEmail(), authorEmail);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get one task by ID",
            responses = {
                    @ApiResponse(description = "The task",
                            useReturnTypeSchema = true)
            })
    public TaskOutputDto getTask(@Parameter(description = "ID of task", required = true)
                                 @PathVariable Long id) {
        Task task = taskService.getTask(id);
        return taskMapper.map(task);
    }

    @GetMapping("/get")
    @Operation(summary = "Get all task by user",
            responses = {
                    @ApiResponse(description = "List of tasks by user",
                            useReturnTypeSchema = true)
            })
    public Page<TaskOutputDto> getAllTasksForUser(@RequestParam @Parameter(description = "Start of the page", required = true)
                                                  int start,
                                                  @RequestParam @Parameter(description = "End of the page", required = true)
                                                  int end,
                                                  @RequestParam @Parameter(description = "E-Mail of the user", required = true)
                                                  String email) {
        if ((end - start) < 1) {
            throw new PaginationOutOfRangeException("Out of range!");
        }

        Pageable pageable = PageRequest.of(start, end - start);
        Page<Task> multipleTasksForUser = taskService.getMultipleTasksForUser(email, pageable);

        return multipleTasksForUser.map(taskMapper::map);
    }
}
