package ru.gavrilovegor519.tasks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.gavrilovegor519.tasks.constant.TaskPriority;
import ru.gavrilovegor519.tasks.constant.TaskStatus;
import ru.gavrilovegor519.tasks.dto.input.tasks.CreateTaskDto;
import ru.gavrilovegor519.tasks.dto.input.tasks.EditTaskDto;
import ru.gavrilovegor519.tasks.entity.Task;
import ru.gavrilovegor519.tasks.entity.User;
import ru.gavrilovegor519.tasks.repo.TaskRepository;
import ru.gavrilovegor519.tasks.repo.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
public class TaskControllerIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword")
            .withExposedPorts(5432)
            .waitingFor(Wait.forListeningPort());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        taskRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void createTask_shouldCreateTask() throws Exception {
        createUser("test@example.com", "password");
        User user2 = createUser("test2@example.com", "password");

        CreateTaskDto createTaskDto = createCreateTaskDto("Test Task", "Test Description", TaskPriority.LOW, TaskStatus.FINISHED, user2.getEmail());
        mockMvc.perform(post("/api/1.0/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTaskDto)))
                .andExpect(status().isOk());

        Task task = taskRepository.findAll().getFirst();
        assertNotNull(task);
        assertEquals("Test Task", task.getName());
        assertEquals("Test Description", task.getDescription());
        assertEquals(TaskPriority.LOW, task.getPriority());
        assertEquals(TaskStatus.FINISHED, task.getStatus());
        assertEquals(user2.getId(), task.getAssigned().getId());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void getTask_shouldReturnTask() throws Exception {
        User user = createUser("test@example.com", "password");
        User user2 = createUser("test2@example.com", "password");

        Task task = createTask("Test Task", "Test Description", user, TaskPriority.LOW, TaskStatus.FINISHED, user2);
        mockMvc.perform(get("/api/1.0/tasks/get/" + task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.name").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void updateTask_shouldUpdateTask() throws Exception {
        User user = createUser("test@example.com", "password");
        User user2 = createUser("test2@example.com", "password");

        Task task = createTask("Original Task", "Original Description", user, TaskPriority.LOW, TaskStatus.FINISHED, user2);

        EditTaskDto editTaskDto = createEditTaskDto("Updated Task", "Updated Description");
        mockMvc.perform(put("/api/1.0/tasks/" + task.getId() + "/edit/description")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editTaskDto)))
                .andExpect(status().isOk());

        Task updatedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getName());
        assertEquals("Updated Description", updatedTask.getDescription());
    }

    private User createUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }

    private Task createTask(String name, String description, User author, TaskPriority priority, TaskStatus status, User assigned) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setAuthor(author);
        task.setPriority(priority);
        task.setStatus(status);
        task.setAssigned(assigned);
        return taskRepository.save(task);
    }

    private CreateTaskDto createCreateTaskDto(String name, String description, TaskPriority priority, TaskStatus status, String assignedEmail) {
        CreateTaskDto createTaskDto = new CreateTaskDto();
        createTaskDto.setName(name);
        createTaskDto.setDescription(description);
        createTaskDto.setPriority(priority);
        createTaskDto.setStatus(status);
        createTaskDto.setAssignedEmail(assignedEmail);
        return createTaskDto;
    }

    private EditTaskDto createEditTaskDto(String name, String description) {
        EditTaskDto editTaskDto = new EditTaskDto();
        editTaskDto.setName(name);
        editTaskDto.setDescription(description);
        return editTaskDto;
    }
}
