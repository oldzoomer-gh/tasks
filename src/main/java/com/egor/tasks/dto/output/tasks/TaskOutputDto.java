package com.egor.tasks.dto.output.tasks;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.dto.output.users.UserDto;
import lombok.Getter;
import lombok.Setter;

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
