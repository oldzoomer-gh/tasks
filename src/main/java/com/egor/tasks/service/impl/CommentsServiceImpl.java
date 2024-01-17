package com.egor.tasks.service.impl;

import com.egor.tasks.controller.schema.dto.change.ChangeCommentsTextDataDto;
import com.egor.tasks.controller.schema.mapper.input.CommentsInputMapper;
import com.egor.tasks.controller.schema.mapper.output.CommentsOutputMapper;
import com.egor.tasks.controller.schema.dto.input.CreateCommentsDto;
import com.egor.tasks.controller.schema.dto.output.OutputCommentsDto;
import com.egor.tasks.dao.entity.Comments;
import com.egor.tasks.dao.entity.Task;
import com.egor.tasks.dao.entity.User;
import com.egor.tasks.exception.CommentNotFound;
import com.egor.tasks.exception.ForbiddenChanges;
import com.egor.tasks.exception.TaskNotFound;
import com.egor.tasks.exception.UserNotFound;
import com.egor.tasks.dao.repo.CommentsRepository;
import com.egor.tasks.dao.repo.TaskRepository;
import com.egor.tasks.dao.repo.UserRepository;
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

        Comments commentToSave = commentsInputMapper.map(comment);

        commentToSave.setAuthor(author);
        commentToSave.setTask(task);

        commentsRepository.save(commentToSave);
    }

    @Override
    public void edit(Long id, ChangeCommentsTextDataDto changes,
                     String email) throws CommentNotFound, UserNotFound, ForbiddenChanges {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        Comments commentToEdit = commentsRepository.findById(id)
                .orElseThrow(() -> new CommentNotFound("Comment not found."));

        if (!commentToEdit.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChanges("Changes of data must do only his author!");
        } else {
            commentToEdit.setText(changes.getText());
            commentsRepository.save(commentToEdit);
        }
    }

    @Override
    public void delete(Long id, String email) throws UserNotFound, ForbiddenChanges, CommentNotFound {
        Comments commentToDelete = commentsRepository.findById(id)
                .orElseThrow(() -> new CommentNotFound("Comment not found."));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        if (!commentToDelete.getAuthor().getEmail().equals(author.getEmail())) {
            throw new ForbiddenChanges("Changes of data must do only his author!");
        } else {
            commentsRepository.deleteById(id);
        }
    }

    @Override
    public OutputCommentsDto getComment(Long id) throws CommentNotFound {
        Comments commentToGet = commentsRepository.findById(id)
                .orElseThrow(() -> new CommentNotFound("Comment not found."));

        return commentsOutputMapper.map(commentToGet);
    }

    @Override
    public Page<OutputCommentsDto> getMultipleCommentsForUser(String email,
                                                              Pageable pageable) throws UserNotFound {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        Page<Comments> commentsByAuthor = commentsRepository.findAllByAuthor(author, pageable);

        return commentsByAuthor.map(commentsOutputMapper::map);
    }

    @Override
    public Page<OutputCommentsDto> getMultipleCommentsForTask(Long taskId,
                                                              Pageable pageable) throws TaskNotFound {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFound("Task not found."));

        Page<Comments> commentsByTask = commentsRepository.findAllByTask(task, pageable);

        return commentsByTask.map(commentsOutputMapper::map);
    }
}
