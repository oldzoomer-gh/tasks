package com.egor.tasks.dto;

import com.egor.tasks.constant.TaskPriority;
import com.egor.tasks.constant.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @Size(max = 100, message = "Name should be less than 100 symbols")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Size(max = 300, message = "The description should be less than 300 symbols")
    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotNull(message = "Status should not be null")
    private TaskStatus status;

    @NotNull(message = "Priority should not be null")
    private TaskPriority priority;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Email(message = "Invalid email")
    @Size(max = 50, message = "Email must be less than 50 characters")
    @NotEmpty(message = "Email can't be empty")
    private String assignedEmail;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto author;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto assigned;
}
