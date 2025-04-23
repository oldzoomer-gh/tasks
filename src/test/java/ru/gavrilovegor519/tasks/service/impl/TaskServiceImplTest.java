package ru.gavrilovegor519.tasks.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gavrilovegor519.tasks.constant.TaskPriority;
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
        User user1 = createUser(0L, "1@1.ru");
        User user2 = createUser(1L, "2@1.ru");
        Task task = createTask(0L, "Test", "Test task.", user1, user2, TaskStatus.PENDING, TaskPriority.LOW);

        when(userRepository.findByEmail("1@1.ru")).thenReturn(Optional.of(user1));
        when(taskRepository.findById(0L)).thenReturn(Optional.of(task));

        taskService.editStatus(0L, TaskStatus.FINISHED, "1@1.ru");
    }

    @Test
    void editStatusAsAssigned() throws UserNotFoundException, ForbiddenChangesException, TaskNotFoundException {
        User user1 = createUser(0L, "1@1.ru");
        User user2 = createUser(1L, "2@1.ru");
        Task task = createTask(0L, "Test", "Test task.", user1, user2, TaskStatus.PENDING, TaskPriority.LOW);

        when(userRepository.findByEmail("2@1.ru")).thenReturn(Optional.of(user2));
        when(taskRepository.findById(0L)).thenReturn(Optional.of(task));

        taskService.editStatus(0L, TaskStatus.FINISHED, "2@1.ru");
    }

    @Test
    void editStatusAsNotAuthorOrAssigned() {
        User user1 = createUser(0L, "1@1.ru");
        User user2 = createUser(1L, "2@1.ru");
        User user3 = createUser(2L, "3@1.ru");
        Task task = createTask(0L, "Test", "Test task.", user1, user2, TaskStatus.PENDING, TaskPriority.LOW);

        when(userRepository.findByEmail("3@1.ru")).thenReturn(Optional.of(user3));
        when(taskRepository.findById(0L)).thenReturn(Optional.of(task));

        assertThrows(ForbiddenChangesException.class,
                () -> taskService.editStatus(0L, TaskStatus.FINISHED, "3@1.ru"));
    }

    private User createUser(Long id, String email) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        return user;
    }

    private Task createTask(Long id, String name, String description, User author, User assigned, TaskStatus status, TaskPriority priority) {
        Task task = new Task();
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setAuthor(author);
        task.setAssigned(assigned);
        task.setStatus(status);
        task.setPriority(priority);
        return task;
    }
}
