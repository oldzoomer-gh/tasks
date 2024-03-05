package com.egor.tasks.dto.change;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangeCommentsTextDataDto {
    @Size(max = 300)
    @NotEmpty
    private String text;
}
