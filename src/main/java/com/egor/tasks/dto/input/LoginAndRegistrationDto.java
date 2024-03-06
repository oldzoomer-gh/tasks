package com.egor.tasks.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAndRegistrationDto {
    @Size(max = 50, message = "Email must be less than 50 characters")
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Invalid email")
    private String email;

    @Size(max = 32, message = "Password must be less than 32 characters")
    @NotEmpty(message = "Password can't be empty")
    private String password;
}
