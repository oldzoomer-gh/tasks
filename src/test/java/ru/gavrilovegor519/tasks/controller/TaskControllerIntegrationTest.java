package ru.gavrilovegor519.tasks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres")
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
    private User user;
    private User user2;

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

        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        userRepository.save(user);

        user2 = new User();
        user2.setEmail("test2@example.com");
        user2.setPassword("password");
        userRepository.save(user2);
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void createTask_shouldCreateTask() throws Exception {
        CreateTaskDto createTaskDto = new CreateTaskDto();
        createTaskDto.setName("Test Task");
        createTaskDto.setDescription("Test Description");
        createTaskDto.setPriority(TaskPriority.LOW);
        createTaskDto.setStatus(TaskStatus.FINISHED);
        createTaskDto.setAssignedEmail(user2.getEmail());

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
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setAuthor(user);
        task.setPriority(TaskPriority.LOW);
        task.setStatus(TaskStatus.FINISHED);
        task.setAssigned(user2);
        task = taskRepository.save(task);
        mockMvc.perform(get("/api/1.0/tasks/get/" + task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.name").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void updateTask_shouldUpdateTask() throws Exception {
        Task task = new Task();
        task.setName("Original Task");
        task.setDescription("Original Description");
        task.setAuthor(user);
        task.setPriority(TaskPriority.LOW);
        task.setStatus(TaskStatus.FINISHED);
        task.setAssigned(user2);
        task = taskRepository.save(task);

        EditTaskDto editTaskDto = new EditTaskDto();
        editTaskDto.setName("Updated Task");
        editTaskDto.setDescription("Updated Description");
        mockMvc.perform(put("/api/1.0/tasks/" + task.getId() + "/edit/description")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editTaskDto)))
                .andExpect(status().isOk());

        Task updatedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getName());
        assertEquals("Updated Description", updatedTask.getDescription());
    }
}
