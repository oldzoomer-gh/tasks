package com.egor.tasks.dto.output;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TokenDto {
    @NotEmpty
    private String token;
}
