package com.egor.tasks.service;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.controller.dto.change.ChangeTaskTextDataDto;
import com.egor.tasks.controller.dto.input.CreateTaskDto;
import com.egor.tasks.controller.dto.output.OutputTaskDto;
import com.egor.tasks.exception.ForbiddenChanges;
import com.egor.tasks.exception.TaskNotFound;
import com.egor.tasks.exception.UserNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    void create(CreateTaskDto task, String email, String assignedEmail) throws UserNotFound;
    void delete(Long id, String email) throws TaskNotFound, UserNotFound, ForbiddenChanges;
    void editStatus(Long id, TaskStatus status, String email) throws TaskNotFound, UserNotFound, ForbiddenChanges;
    void editPriority(Long id, TaskPriority priority, String email) throws TaskNotFound, UserNotFound, ForbiddenChanges;
    void editNameAndDescription(Long id,
                                ChangeTaskTextDataDto taskNameAndDescription,
                                String email) throws TaskNotFound, UserNotFound, ForbiddenChanges;
    void editAssignedUser(Long id, String assignedEmail, String email) throws TaskNotFound, UserNotFound, ForbiddenChanges;
    OutputTaskDto getTask(Long id) throws TaskNotFound;
    Page<OutputTaskDto> getMultipleTasksForUser(String email, Pageable pageable) throws UserNotFound;
}
