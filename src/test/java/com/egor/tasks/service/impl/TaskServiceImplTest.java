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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void editStatusAsAuthor() throws UserNotFoundException, ForbiddenChangesException, TaskNotFoundException {

        var user1 = new User();
        user1.setId(0L);
        user1.setEmail("1@1.ru");

        var user2 = new User();
        user2.setId(1L);
        user2.setEmail("2@1.ru");

        var task = new Task();
        task.setId(0L);
        task.setName("Test");
        task.setDescription("Test task.");
        task.setAuthor(user1);
        task.setAssigned(user2);
        task.setStatus(TaskStatus.PENDING);
        task.setPriority(TaskPriority.LOW);

        when(userRepository.findByEmail("1@1.ru")).thenReturn(Optional.of(user1));
        when(taskRepository.findById(0L)).thenReturn(Optional.of(task));

        taskService.editStatus(0L, TaskStatus.FINISHED, "1@1.ru");
    }

    @Test
    void editStatusAsAssigned() throws UserNotFoundException, ForbiddenChangesException, TaskNotFoundException {

        var user1 = new User();
        user1.setId(0L);
        user1.setEmail("1@1.ru");

        var user2 = new User();
        user2.setId(1L);
        user2.setEmail("2@1.ru");

        var task = new Task();
        task.setId(0L);
        task.setName("Test");
        task.setDescription("Test task.");
        task.setAuthor(user1);
        task.setAssigned(user2);
        task.setStatus(TaskStatus.PENDING);
        task.setPriority(TaskPriority.LOW);

        when(userRepository.findByEmail("2@1.ru")).thenReturn(Optional.of(user2));
        when(taskRepository.findById(0L)).thenReturn(Optional.of(task));

        taskService.editStatus(0L, TaskStatus.FINISHED, "2@1.ru");
    }

    @Test
    void editStatusAsNotAuthorOrAssigned() {

        var user1 = new User();
        user1.setId(0L);
        user1.setEmail("1@1.ru");

        var user2 = new User();
        user2.setId(1L);
        user2.setEmail("2@1.ru");

        var user3 = new User();
        user3.setId(2L);
        user3.setEmail("3@1.ru");

        var task = new Task();
        task.setId(0L);
        task.setName("Test");
        task.setDescription("Test task.");
        task.setAuthor(user1);
        task.setAssigned(user2);
        task.setStatus(TaskStatus.PENDING);
        task.setPriority(TaskPriority.LOW);

        when(userRepository.findByEmail("3@1.ru")).thenReturn(Optional.of(user3));
        when(taskRepository.findById(0L)).thenReturn(Optional.of(task));

        assertThrows(ForbiddenChangesException.class,
                () -> taskService.editStatus(0L, TaskStatus.FINISHED, "3@1.ru"));
    }
}