package ru.gavrilovegor519.tasks.dto.input.tasks;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.gavrilovegor519.tasks.constant.TaskPriority;

@Getter
@Setter
public class EditTaskPriorityDto {
    @NotNull
    private Long taskId;

    @NotNull
    private TaskPriority priority;
}
