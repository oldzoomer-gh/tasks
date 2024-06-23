package com.egor.tasks.dto.input.tasks;

import com.egor.tasks.constant.TaskPriority;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditTaskPriorityDto {
    @NotNull
    private Long taskId;

    @NotNull
    private TaskPriority priority;
}
