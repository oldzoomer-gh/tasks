package com.egor.tasks.dto.change;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeTaskTextDataDto {
    @Size(max = 100, message = "Text is too long")
    @NotEmpty(message = "Text is empty")
    private String name;

    @Size(max = 300, message = "Text is too long")
    @NotEmpty(message = "Text is empty")
    private String description;
}
