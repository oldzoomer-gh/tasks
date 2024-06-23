package com.egor.tasks.dto.input.tasks;

import com.egor.tasks.constant.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditTaskStatusDto {
    @NotNull
    private Long taskId;

    @NotNull
    private TaskStatus status;
}
