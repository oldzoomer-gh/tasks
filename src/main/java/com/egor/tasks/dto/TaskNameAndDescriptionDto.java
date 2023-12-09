package com.egor.tasks.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskNameAndDescriptionDto {
    @Size(max = 100)
    @NotEmpty
    private String name;

    @Size(max = 300)
    @NotEmpty
    private String description;
}
