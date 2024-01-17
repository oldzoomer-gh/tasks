package com.egor.tasks.service.impl;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.controller.schema.dto.change.ChangeTaskTextDataDto;
import com.egor.tasks.controller.schema.mapper.input.TaskInputMapper;
import com.egor.tasks.controller.schema.mapper.output.TaskOutputMapper;
import com.egor.tasks.controller.schema.dto.input.CreateTaskDto;
import com.egor.tasks.controller.schema.dto.output.OutputTaskDto;
import com.egor.tasks.dao.entity.Task;
import com.egor.tasks.dao.entity.User;
import com.egor.tasks.exception.ForbiddenChanges;
import com.egor.tasks.exception.TaskNotFound;
import com.egor.tasks.exception.UserNotFound;
import com.egor.tasks.dao.repo.TaskRepository;
import com.egor.tasks.dao.repo.UserRepository;
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
    public void create(CreateTaskDto task, String email, String assignedEmail) throws UserNotFound {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("Author not found."));
        User assignedUser = userRepository.findByEmail(assignedEmail)
                .orElseThrow(() -> new UserNotFound("Assigned user not found."));

        Task taskToSave = taskInputMapper.map(task);

        taskToSave.setAuthor(author);
        taskToSave.setAssigned(assignedUser);

        taskRepository.save(taskToSave);
    }

    @Override
    public void delete(Long id, String email)
            throws TaskNotFound, UserNotFound, ForbiddenChanges {
        Task taskToDelete = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        if (!taskToDelete.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChanges("Changes of data must do only his author!");
        } else {
            taskRepository.deleteById(id);
        }
    }

    @Override
    public void editStatus(Long id, TaskStatus status, String email)
            throws TaskNotFound, UserNotFound, ForbiddenChanges {
        Task taskToEdit = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        User authorOrAssignedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        if (!taskToEdit.getAuthor().getEmail().equals(authorOrAssignedUser.getEmail())
                && !taskToEdit.getAssigned().getEmail().equals(authorOrAssignedUser.getEmail())) {
            throw new ForbiddenChanges("Changes of data must do only his author, or assigned user!");
        } else {
            taskToEdit.setStatus(status);
            taskRepository.save(taskToEdit);
        }
    }

    @Override
    public void editPriority(Long id, TaskPriority priority, String email)
            throws TaskNotFound, UserNotFound, ForbiddenChanges {
        Task taskToEdit = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        if (!taskToEdit.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChanges("Changes of data must do only his author!");
        } else {
            taskToEdit.setPriority(priority);
            taskRepository.save(taskToEdit);
        }
    }

    @Override
    public void editNameAndDescription(Long id, ChangeTaskTextDataDto taskNameAndDescription,
                                       String email) throws TaskNotFound, UserNotFound, ForbiddenChanges {
        Task taskToEdit = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        if (!taskToEdit.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChanges("Changes of data must do only his author!");
        } else {
            taskToEdit.setName(taskNameAndDescription.getName());
            taskToEdit.setDescription(taskNameAndDescription.getDescription());
            taskRepository.save(taskToEdit);
        }
    }

    @Override
    public void editAssignedUser(Long id, String assignedEmail, String email)
            throws TaskNotFound, UserNotFound, ForbiddenChanges {
        Task taskToEdit = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        User assignedUser = userRepository.findByEmail(assignedEmail)
                .orElseThrow(() -> new UserNotFound("Assigned user not found!"));

        if (!taskToEdit.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChanges("Changes of data must do only his author!");
        } else {
            taskToEdit.setAssigned(assignedUser);
            taskRepository.save(taskToEdit);
        }
    }

    @Override
    public OutputTaskDto getTask(Long id) throws TaskNotFound {
        Task taskToGet = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        return taskOutputMapper.map(taskToGet);
    }

    @Override
    public Page<OutputTaskDto> getMultipleTasksForUser(String email, Pageable pageable) throws UserNotFound {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        Page<Task> tasksByAuthor = taskRepository.findAllByAuthor(author, pageable);

        return tasksByAuthor.map(taskOutputMapper::map);
    }
}
