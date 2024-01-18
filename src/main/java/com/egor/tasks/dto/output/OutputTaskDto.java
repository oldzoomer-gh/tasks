package com.egor.tasks.dto.output;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
    private OutputUserDto author;

    @NotNull
    private OutputUserDto assigned;
}
