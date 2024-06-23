package com.egor.tasks.service.impl;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.entity.Task;
import com.egor.tasks.entity.User;
import com.egor.tasks.exception.ForbiddenChangesException;
import com.egor.tasks.exception.TaskNotFoundException;
import com.egor.tasks.exception.UserNotFoundException;
import com.egor.tasks.repo.TaskRepository;
import com.egor.tasks.repo.UserRepository;
import com.egor.tasks.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public void create(Task task, String email, String assignedEmail) {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Author not found."));
        User assignedUser = userRepository.findByEmail(assignedEmail)
                .orElseThrow(() -> new UserNotFoundException("Assigned user not found."));

        task.setAuthor(author);
        task.setAssigned(assignedUser);

        taskRepository.save(task);
    }

    @Override
    public void delete(Long id, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        if (!task.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChangesException("Changes of data must do only his author!");
        } else {
            User assignedUser = task.getAssigned();
            assignedUser.getAssignedTasks().remove(task);
            userRepository.save(assignedUser);
            author.getAuthorTasks().remove(task);
            userRepository.save(author);
        }
    }

    @Override
    public void editStatus(Long id, TaskStatus status, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        if (!task.getAuthor().getEmail().equals(user.getEmail())
                && !task.getAssigned().getEmail().equals(user.getEmail())) {
            throw new ForbiddenChangesException("Changes of data must do only his author, or assigned user!");
        } else {
            task.setStatus(status);
            taskRepository.save(task);
        }
    }

    @Override
    public void editPriority(Long id, TaskPriority priority, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        if (!task.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChangesException("Changes of data must do only his author!");
        } else {
            task.setPriority(priority);
            taskRepository.save(task);
        }
    }

    @Override
    public void editNameAndDescription(
            Long id, Task task, String email) {
        Task task1 = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        if (!task.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChangesException("Changes of data must do only his author!");
        } else {
            task1.setName(task.getName());
            task1.setDescription(task.getDescription());
            taskRepository.save(task1);
        }
    }

    @Override
    public void editAssignedUser(Long id, String assignedEmail, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        User assignedUser = userRepository.findByEmail(assignedEmail)
                .orElseThrow(() -> new UserNotFoundException("Assigned user not found!"));

        if (!task.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChangesException("Changes of data must do only his author!");
        } else {
            task.setAssigned(assignedUser);
            taskRepository.save(task);
        }
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));
    }

    @Override
    public Page<Task> getMultipleTasksForUser(String email, Pageable pageable) {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        return taskRepository.findAllByAuthor(author, pageable);
    }
}
