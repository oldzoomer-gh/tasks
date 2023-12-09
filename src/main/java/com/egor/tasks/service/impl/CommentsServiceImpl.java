package com.egor.tasks.service.impl;

import com.egor.tasks.converters.input.CommentsInputMapper;
import com.egor.tasks.converters.output.CommentsOutputMapper;
import com.egor.tasks.dto.change.ChangeCommentsTextDataDto;
import com.egor.tasks.dto.input.CreateCommentsDto;
import com.egor.tasks.dto.output.OutputCommentsDto;
import com.egor.tasks.entity.Comments;
import com.egor.tasks.entity.Task;
import com.egor.tasks.entity.User;
import com.egor.tasks.exception.CommentNotFound;
import com.egor.tasks.exception.ForbiddenChanges;
import com.egor.tasks.exception.TaskNotFound;
import com.egor.tasks.exception.UserNotFound;
import com.egor.tasks.repo.CommentsRepository;
import com.egor.tasks.repo.TaskRepository;
import com.egor.tasks.repo.UserRepository;
import com.egor.tasks.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final UserRepository userRepository;
    private final CommentsRepository commentsRepository;
    private final TaskRepository taskRepository;
    private final CommentsInputMapper commentsInputMapper;
    private final CommentsOutputMapper commentsOutputMapper;

    @Override
    public void create(CreateCommentsDto comment, Long taskId, String email)
            throws UserNotFound, TaskNotFound {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("Author not found."));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFound("Task not found!"));

        Comments comment1 = commentsInputMapper.toEntity(comment);

        comment1.setAuthor(author);
        comment1.setTask(task);

        commentsRepository.save(comment1);
    }

    @Override
    public void edit(Long id, ChangeCommentsTextDataDto changes,
                     String email) throws CommentNotFound, UserNotFound, ForbiddenChanges {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        Comments comment = commentsRepository.findById(id)
                .orElseThrow(() -> new CommentNotFound("Comment not found."));

        if (!comment.getAuthor().equals(author)) {
            throw new ForbiddenChanges("Changes of data must do only his author!");
        } else {
            comment.setText(changes.getText());
            commentsRepository.save(comment);
        }
    }

    @Override
    public OutputCommentsDto getComment(Long id) throws CommentNotFound {
        Comments comment = commentsRepository.findById(id)
                .orElseThrow(() -> new CommentNotFound("Comment not found."));

        return commentsOutputMapper.map(comment);
    }

    @Override
    public Page<OutputCommentsDto> getMultipleCommentsForUser(String email,
                                                              Pageable pageable) throws UserNotFound {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        Page<Comments> comments = commentsRepository.findAllByAuthor(author, pageable);

        return comments.map(commentsOutputMapper::map);
    }

    @Override
    public Page<OutputCommentsDto> getMultipleCommentsForTask(Long taskId,
                                                              Pageable pageable) throws TaskNotFound {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        Page<Comments> comments = commentsRepository.findAllByTask(task, pageable);

        return comments.map(commentsOutputMapper::map);
    }
}
