package com.egor.tasks.dto.output;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutputTaskDto {
    private String name;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private OutputUserDto author;

    @NotNull
    private OutputUserDto assigned;
}
