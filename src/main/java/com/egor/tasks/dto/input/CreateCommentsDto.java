package com.egor.tasks.dto.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCommentsDto {
    @Size(max = 300)
    @NotEmpty
    private String text;
}
