package com.egor.tasks.dto.output;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
    @NotEmpty
    private String token;
}
