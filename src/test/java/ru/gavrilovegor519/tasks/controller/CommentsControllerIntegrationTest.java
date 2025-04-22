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
import ru.gavrilovegor519.tasks.dto.input.comments.CreateCommentDto;
import ru.gavrilovegor519.tasks.dto.input.comments.EditCommentDto;
import ru.gavrilovegor519.tasks.entity.Comments;
import ru.gavrilovegor519.tasks.entity.Task;
import ru.gavrilovegor519.tasks.entity.User;
import ru.gavrilovegor519.tasks.repo.CommentsRepository;
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
public class CommentsControllerIntegrationTest {

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
    @Autowired
    private CommentsRepository commentsRepository;
    private User user;
    private Task task;

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
        commentsRepository.deleteAll();

        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        userRepository.save(user);

        User user2 = new User();
        user2.setEmail("test2@example.com");
        user2.setPassword("password");
        userRepository.save(user2);

        task = new Task();
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setAuthor(user);
        task.setPriority(TaskPriority.LOW);
        task.setStatus(TaskStatus.FINISHED);
        task.setAssigned(user2);
        taskRepository.save(task);
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void createComment_shouldCreateComment() throws Exception {
        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setTaskId(task.getId());
        createCommentDto.setText("Test comment");

        mockMvc.perform(post("/api/1.0/comments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommentDto)))
                .andExpect(status().isOk());

        Comments comment = commentsRepository.findAll().getFirst();
        assertNotNull(comment);
        assertEquals("Test comment", comment.getText());
        assertEquals("test@example.com", comment.getAuthor().getEmail());
        assertEquals(task.getId(), comment.getTask().getId());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void getAllCommentsForUser_shouldReturnComments() throws Exception {
        Comments comment = new Comments();
        comment.setText("Test comment");
        comment.setAuthor(user);
        comment.setTask(task);
        commentsRepository.save(comment);

        mockMvc.perform(get("/api/1.0/comments/get/user")
                        .param("start", "0")
                        .param("end", "10")
                        .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(comment.getId()))
                .andExpect(jsonPath("$.content[0].text").value("Test comment"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void getAllCommentsForTask_shouldReturnComments() throws Exception {
        Comments comment = new Comments();
        comment.setText("Test comment");
        comment.setAuthor(user);
        comment.setTask(task);
        commentsRepository.save(comment);

        mockMvc.perform(get("/api/1.0/comments/get/task")
                        .param("start", "0")
                        .param("end", "10")
                        .param("taskId", task.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(comment.getId()))
                .andExpect(jsonPath("$.content[0].text").value("Test comment"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void getComment_shouldReturnComment() throws Exception {
        Comments comment = new Comments();
        comment.setText("Test comment");
        comment.setAuthor(user);
        comment.setTask(task);
        comment = commentsRepository.save(comment);

        mockMvc.perform(get("/api/1.0/comments/get/" + comment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(comment.getId()))
                .andExpect(jsonPath("$.text").value("Test comment"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void editComment_shouldEditComment() throws Exception {
        Comments comment = new Comments();
        comment.setText("Original comment");
        comment.setAuthor(user);
        comment.setTask(task);
        commentsRepository.save(comment);

        EditCommentDto editCommentDto = new EditCommentDto();
        editCommentDto.setCommentId(comment.getId());
        editCommentDto.setText("Updated comment");

        mockMvc.perform(put("/api/1.0/comments/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editCommentDto)))
                .andExpect(status().isOk());

        Comments updatedComment = commentsRepository.findById(comment.getId()).orElseThrow();
        assertNotNull(updatedComment);
        assertEquals("Updated comment", updatedComment.getText());
    }
}
