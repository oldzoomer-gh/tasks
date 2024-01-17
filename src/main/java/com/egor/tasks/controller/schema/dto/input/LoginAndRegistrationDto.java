package com.egor.tasks.controller.schema.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginAndRegistrationDto {
    @Size(max = 50)
    @NotEmpty
    @Email
    private String email;

    @Size(max = 32)
    @NotEmpty
    private String password;
}
