package com.egor.tasks.dto.output;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OutputCommentsDto {
    @NotNull
    private OutputUserDto author;

    @NotNull
    private OutputTaskDto task;

    @Size(max = 300)
    @NotEmpty
    private String text;
}
