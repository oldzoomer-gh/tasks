package com.egor.tasks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @Size(max = 50, message = "Email must be less than 50 characters")
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Invalid email")
    private String email;

    @Size(max = 32, min = 8, message = "Password must be between 8 and 32 characters")
    @NotEmpty(message = "Password can't be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
