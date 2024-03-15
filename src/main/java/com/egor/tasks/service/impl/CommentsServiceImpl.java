package com.egor.tasks.service.impl;

import com.egor.tasks.dto.CommentDto;
import com.egor.tasks.entity.Comments;
import com.egor.tasks.entity.Task;
import com.egor.tasks.entity.User;
import com.egor.tasks.exception.CommentNotFoundException;
import com.egor.tasks.exception.ForbiddenChangesException;
import com.egor.tasks.exception.TaskNotFoundException;
import com.egor.tasks.exception.UserNotFoundException;
import com.egor.tasks.mapper.CommentMapper;
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
    private final CommentMapper commentMapper;

    @Override
    public void create(CommentDto comment, Long taskId, String email) {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Author not found."));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found!"));

        Comments comment1 = commentMapper.map(comment);

        comment1.setAuthor(author);
        comment1.setTask(task);

        commentsRepository.save(comment1);
    }

    @Override
    public void edit(Long id, CommentDto changes,
                     String email) {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        Comments comment = commentsRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found."));

        if (!comment.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChangesException("Changes of data must do only his author!");
        } else {
            comment.setText(changes.getText());
            commentsRepository.save(comment);
        }
    }

    @Override
    public void delete(Long id, String email) {
        Comments comments = commentsRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        if (!comments.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChangesException("Changes of data must do only his author!");
        } else {
            comments.getTask().getComments().remove(comments);
            taskRepository.save(comments.getTask());
            author.getComments().remove(comments);
            userRepository.save(author);
        }
    }

    @Override
    public CommentDto getComment(Long id) {
        Comments comment = commentsRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found."));

        return commentMapper.map(comment);
    }

    @Override
    public Page<CommentDto> getMultipleCommentsForUser(String email,
                                                              Pageable pageable) {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        Page<Comments> comments = commentsRepository.findAllByAuthor(author, pageable);

        return comments.map(commentMapper::map);
    }

    @Override
    public Page<CommentDto> getMultipleCommentsForTask(Long taskId,
                                                              Pageable pageable) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));

        Page<Comments> comments = commentsRepository.findAllByTask(task, pageable);

        return comments.map(commentMapper::map);
    }
}
