package com.egor.tasks.service;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Task management service layer.
 * @author Egor Gavrilov (gavrilovegor519@gmail.com)
 */
public interface TaskService {
    /**
     * Creates a new task.
     * @param task Task to create
     * @param email Email of the user who creates the task
     * @param assignedEmail Email of the user assigned to the task
     */
    void create(TaskDto task, String email, String assignedEmail);

    /**
     * Deletes a task.
     * @param id ID of the task to delete
     * @param email Email of the user who deletes the task
     */
    void delete(Long id, String email);

    /**
     * Updates a task status.
     * @param id ID of the task to update
     * @param status New status of the task
     * @param email Email of the user who updates the task
     */
    void editStatus(Long id, TaskStatus status, String email);

    /**
     * Updates a task priority.
     * @param id ID of the task to update
     * @param priority New priority of the task
     * @param email Email of the user who updates the task
     */
    void editPriority(Long id, TaskPriority priority, String email);

    /**
     * Updates a task text.
     * @param id ID of the task to update
     * @param taskNameAndDescription New text of the task
     * @param email Email of the user who updates the task
     */
    void editNameAndDescription(Long id, TaskDto taskNameAndDescription, String email);

    /**
     * Edit assigned user.
     * @param id ID of the task to update
     * @param assignedEmail New assigned user of the task.
     * @param email Email of the user who updates the task.
     */
    void editAssignedUser(Long id, String assignedEmail, String email);

    /**
     * Getting a task by ID.
     * @param id ID of the task to get.
     * @return A task.
     */
    TaskDto getTask(Long id);

    /**
     * Getting all tasks for a user why created.
     * @param email Email of the user.
     * @param pageable Paging information. Page number and page size.
     * @return A list of tasks.
     */
    Page<TaskDto> getMultipleTasksForUser(String email, Pageable pageable);
}
