package com.egor.tasks.controller.schema.dto.change;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeCommentsTextDataDto {
    @Size(max = 300)
    @NotEmpty
    private String text;
}
