package com.egor.tasks.dto.output;

import com.egor.tasks.entity.Task;
import com.egor.tasks.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OutputCommentsDto {
    @NotNull
    private User author;

    @NotNull
    private Task task;

    @Size(max = 300)
    @NotEmpty
    private String text;
}
