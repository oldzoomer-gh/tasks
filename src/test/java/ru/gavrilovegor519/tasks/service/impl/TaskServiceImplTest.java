package ru.gavrilovegor519.tasks.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gavrilovegor519.tasks.constant.TaskStatus;
import ru.gavrilovegor519.tasks.entity.Task;
import ru.gavrilovegor519.tasks.entity.User;
import ru.gavrilovegor519.tasks.exception.ForbiddenChangesException;
import ru.gavrilovegor519.tasks.exception.TaskNotFoundException;
import ru.gavrilovegor519.tasks.exception.UserNotFoundException;
import ru.gavrilovegor519.tasks.repo.TaskRepository;
import ru.gavrilovegor519.tasks.repo.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
        User user1 = mock(User.class);
        Task task = mock(Task.class);

        when(user1.getEmail()).thenReturn("1@1.ru");
        when(task.getAuthor()).thenReturn(user1);
        when(userRepository.findByEmail("1@1.ru")).thenReturn(Optional.of(user1));
        when(taskRepository.findById(0L)).thenReturn(Optional.of(task));

        taskService.editStatus(0L, TaskStatus.FINISHED, "1@1.ru");

        verify(task).setStatus(TaskStatus.FINISHED);
    }

    @Test
    void editStatusAsAssigned() throws UserNotFoundException, ForbiddenChangesException, TaskNotFoundException {
        User user1 = mock(User.class);
        User user2 = mock(User.class);
        Task task = mock(Task.class);

        when(user1.getEmail()).thenReturn("1@1.ru");
        when(user2.getEmail()).thenReturn("2@1.ru");
        when(task.getAuthor()).thenReturn(user1);
        when(task.getAssigned()).thenReturn(user2);
        when(userRepository.findByEmail("2@1.ru")).thenReturn(Optional.of(user2));
        when(taskRepository.findById(0L)).thenReturn(Optional.of(task));

        taskService.editStatus(0L, TaskStatus.FINISHED, "2@1.ru");

        verify(task).setStatus(TaskStatus.FINISHED);
    }

    @Test
    void editStatusAsNotAuthorOrAssigned() {
        User user1 = mock(User.class);
        User user2 = mock(User.class);
        User user3 = mock(User.class);
        Task task = mock(Task.class);

        when(user1.getEmail()).thenReturn("1@1.ru");
        when(user2.getEmail()).thenReturn("2@1.ru");
        when(user3.getEmail()).thenReturn("3@1.ru");
        when(task.getAuthor()).thenReturn(user1);
        when(task.getAssigned()).thenReturn(user2);
        when(userRepository.findByEmail("3@1.ru")).thenReturn(Optional.of(user3));
        when(taskRepository.findById(0L)).thenReturn(Optional.of(task));

        assertThrows(ForbiddenChangesException.class,
                () -> taskService.editStatus(0L, TaskStatus.FINISHED, "3@1.ru"));
    }
}
