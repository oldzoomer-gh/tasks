package com.egor.tasks.service;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.dto.TaskDto;
import com.egor.tasks.dto.TaskNameAndDescriptionDto;
import com.egor.tasks.exception.UserNotFound;
import org.springframework.data.domain.Page;

public interface TaskService {
    void create(TaskDto task);
    void delete(Long id);
    void editStatus(Long id, TaskStatus status);
    void editPriority(Long id, TaskPriority priority);
    void editNameAndDescription(Long id, TaskNameAndDescriptionDto taskNameAndDescription);
    TaskDto getTask(Long id);
    Page<TaskDto> getMultipleTasksForCurrentUser();
    Page<TaskDto> getMultipleTasksForAnotherUser(String email) throws UserNotFound;
}
