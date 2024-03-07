package com.egor.tasks.service.impl;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.dto.change.ChangeTaskTextDataDto;
import com.egor.tasks.dto.input.CreateTaskDto;
import com.egor.tasks.dto.output.OutputTaskDto;
import com.egor.tasks.entity.Task;
import com.egor.tasks.entity.User;
import com.egor.tasks.exception.ForbiddenChanges;
import com.egor.tasks.exception.TaskNotFound;
import com.egor.tasks.exception.UserNotFound;
import com.egor.tasks.mapper.input.TaskInputMapper;
import com.egor.tasks.mapper.output.TaskOutputMapper;
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
    private final TaskInputMapper taskInputMapper;
    private final TaskOutputMapper taskOutputMapper;

    @Override
    public void create(CreateTaskDto task, String email, String assignedEmail) {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("Author not found."));
        User assignedUser = userRepository.findByEmail(assignedEmail)
                .orElseThrow(() -> new UserNotFound("Assigned user not found."));

        Task task1 = taskInputMapper.map(task);

        task1.setAuthor(author);
        task1.setAssigned(assignedUser);

        taskRepository.save(task1);
    }

    @Override
    public void delete(Long id, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        if (!task.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChanges("Changes of data must do only his author!");
        } else {
            taskRepository.deleteById(id);
        }
    }

    @Override
    public void editStatus(Long id, TaskStatus status, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        if (!task.getAuthor().getEmail().equals(user.getEmail())
                && !task.getAssigned().getEmail().equals(user.getEmail())) {
            throw new ForbiddenChanges("Changes of data must do only his author, or assigned user!");
        } else {
            task.setStatus(status);
            taskRepository.save(task);
        }
    }

    @Override
    public void editPriority(Long id, TaskPriority priority, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        if (!task.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChanges("Changes of data must do only his author!");
        } else {
            task.setPriority(priority);
            taskRepository.save(task);
        }
    }

    @Override
    public void editNameAndDescription(
            Long id, ChangeTaskTextDataDto taskNameAndDescription, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        if (!task.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChanges("Changes of data must do only his author!");
        } else {
            task.setName(taskNameAndDescription.getName());
            task.setDescription(taskNameAndDescription.getDescription());
            taskRepository.save(task);
        }
    }

    @Override
    public void editAssignedUser(Long id, String assignedEmail, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        User assignedUser = userRepository.findByEmail(assignedEmail)
                .orElseThrow(() -> new UserNotFound("Assigned user not found!"));

        if (!task.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChanges("Changes of data must do only his author!");
        } else {
            task.setAssigned(assignedUser);
            taskRepository.save(task);
        }
    }

    @Override
    public OutputTaskDto getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        return taskOutputMapper.map(task);
    }

    @Override
    public Page<OutputTaskDto> getMultipleTasksForUser(String email, Pageable pageable) {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        Page<Task> tasks = taskRepository.findAllByAuthor(author, pageable);

        return tasks.map(taskOutputMapper::map);
    }
}
