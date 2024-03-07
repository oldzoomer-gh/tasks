package com.egor.tasks.service;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.dto.change.ChangeTaskTextDataDto;
import com.egor.tasks.dto.input.CreateTaskDto;
import com.egor.tasks.dto.output.OutputTaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    void create(CreateTaskDto task, String email, String assignedEmail);
    void delete(Long id, String email);
    void editStatus(Long id, TaskStatus status, String email);
    void editPriority(Long id, TaskPriority priority, String email);
    void editNameAndDescription(Long id, ChangeTaskTextDataDto taskNameAndDescription, String email);
    void editAssignedUser(Long id, String assignedEmail, String email);
    OutputTaskDto getTask(Long id);
    Page<OutputTaskDto> getMultipleTasksForUser(String email, Pageable pageable);
}
