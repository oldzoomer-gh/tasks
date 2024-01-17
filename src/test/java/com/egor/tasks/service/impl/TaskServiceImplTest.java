package com.egor.tasks.service.impl;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.dao.entity.Task;
import com.egor.tasks.dao.entity.User;
import com.egor.tasks.exception.ForbiddenChanges;
import com.egor.tasks.exception.TaskNotFound;
import com.egor.tasks.exception.UserNotFound;
import com.egor.tasks.dao.repo.TaskRepository;
import com.egor.tasks.dao.repo.UserRepository;
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
    void editStatusAsAuthor() throws UserNotFound, ForbiddenChanges, TaskNotFound {
        User user1 = User.builder()
                .id(0L)
                .email("1@1.ru")
                .build();

        User user2 = User.builder()
                .id(1L)
                .email("2@1.ru")
                .build();

        Task task = Task.builder()
                .id(0L)
                .name("Test")
                .description("Test task.")
                .author(user1)
                .assigned(user2)
                .status(TaskStatus.PENDING)
                .priority(TaskPriority.LOW)
                .build();

        when(userRepository.findByEmail("1@1.ru")).thenReturn(Optional.of(user1));
        when(taskRepository.findById(0L)).thenReturn(Optional.of(task));

        taskService.editStatus(0L, TaskStatus.FINISHED, "1@1.ru");
    }

    @Test
    void editStatusAsAssigned() throws UserNotFound, ForbiddenChanges, TaskNotFound {
        User user1 = User.builder()
                .id(0L)
                .email("1@1.ru")
                .build();

        User user2 = User.builder()
                .id(1L)
                .email("2@1.ru")
                .build();

        Task task = Task.builder()
                .id(0L)
                .name("Test")
                .description("Test task.")
                .author(user1)
                .assigned(user2)
                .status(TaskStatus.PENDING)
                .priority(TaskPriority.LOW)
                .build();

        when(userRepository.findByEmail("2@1.ru")).thenReturn(Optional.of(user2));
        when(taskRepository.findById(0L)).thenReturn(Optional.of(task));

        taskService.editStatus(0L, TaskStatus.FINISHED, "2@1.ru");
    }

    @Test
    void editStatusAsNotAuthorOrAssigned() throws UserNotFound, ForbiddenChanges, TaskNotFound {
        User user1 = User.builder()
                .id(0L)
                .email("1@1.ru")
                .build();

        User user2 = User.builder()
                .id(1L)
                .email("2@1.ru")
                .build();

        User user3 = User.builder()
                .id(2L)
                .email("3@1.ru")
                .build();

        Task task = Task.builder()
                .id(0L)
                .name("Test")
                .description("Test task.")
                .author(user1)
                .assigned(user2)
                .status(TaskStatus.PENDING)
                .priority(TaskPriority.LOW)
                .build();

        when(userRepository.findByEmail("3@1.ru")).thenReturn(Optional.of(user3));
        when(taskRepository.findById(0L)).thenReturn(Optional.of(task));

        assertThrows(ForbiddenChanges.class,
                () -> taskService.editStatus(0L, TaskStatus.FINISHED, "3@1.ru"));
    }
}