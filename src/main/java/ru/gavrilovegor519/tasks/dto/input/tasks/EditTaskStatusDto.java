package ru.gavrilovegor519.tasks.dto.input.tasks;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.gavrilovegor519.tasks.constant.TaskStatus;

@Getter
@Setter
public class EditTaskStatusDto {
    @NotNull
    private Long taskId;

    @NotNull
    private TaskStatus status;
}
