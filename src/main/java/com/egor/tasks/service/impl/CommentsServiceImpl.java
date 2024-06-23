package com.egor.tasks.service.impl;

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
    public void create(Comments comment, Long taskId, String email) {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Author not found."));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found!"));

        comment.setAuthor(author);
        comment.setTask(task);

        commentsRepository.save(comment);
    }

    @Override
    public void edit(Long id, Comments changes,
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
            Task task = comments.getTask();
            task.getComments().remove(comments);
            taskRepository.save(task);
            author.getComments().remove(comments);
            userRepository.save(author);
        }
    }

    @Override
    public Comments getComment(Long id) {
        return commentsRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found."));
    }

    @Override
    public Page<Comments> getMultipleCommentsForUser(String email,
                                                              Pageable pageable) {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        return commentsRepository.findAllByAuthor(author, pageable);
    }

    @Override
    public Page<Comments> getMultipleCommentsForTask(Long taskId,
                                                              Pageable pageable) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));

        return commentsRepository.findAllByTask(task, pageable);
    }
}
