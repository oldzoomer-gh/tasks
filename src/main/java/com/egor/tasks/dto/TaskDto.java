package com.egor.tasks.dto;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
    @Size(max = 100, message = "Name should be less than 100 symbols")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Size(max = 300, message = "Description should be less than 300 symbols")
    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotNull(message = "Status should not be null")
    private TaskStatus status;

    @NotNull(message = "Priority should not be null")
    private TaskPriority priority;

    private UserDto author;

    private UserDto assigned;
}
