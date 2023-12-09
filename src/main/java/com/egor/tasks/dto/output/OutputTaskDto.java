package com.egor.tasks.dto.output;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.egor.tasks.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OutputTaskDto {
    @Size(max = 100)
    @NotEmpty
    private String name;

    @Size(max = 300)
    @NotEmpty
    private String description;

    @NotNull
    private TaskStatus status;

    @NotNull
    private TaskPriority priority;

    @NotNull
    private User author;

    @NotNull
    private User assigned;
}
