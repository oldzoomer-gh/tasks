package ru.gavrilovegor519.tasks.dto.output.tasks;

import lombok.Getter;
import lombok.Setter;
import ru.gavrilovegor519.tasks.constant.TaskPriority;
import ru.gavrilovegor519.tasks.constant.TaskStatus;
import ru.gavrilovegor519.tasks.dto.output.users.UserDto;

@Getter
@Setter
public class TaskOutputDto {
    private Long id;

    private String name;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private UserDto author;

    private UserDto assigned;
}
