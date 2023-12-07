package com.egor.tasks.service;

import com.egor.tasks.dto.TaskDto;
import com.egor.tasks.exception.UserNotFound;
import org.springframework.data.domain.Page;

public interface TaskService {
    void create(TaskDto task);
    void delete(Long id);
    void edit(Long id, TaskDto changes);
    TaskDto getTask(Long id);
    Page<TaskDto> getMultipleTasksForCurrentUser();
    Page<TaskDto> getMultipleTasksForAnotherUser(String email) throws UserNotFound;
}
