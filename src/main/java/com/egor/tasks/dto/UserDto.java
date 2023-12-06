package com.egor.tasks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class UserDto {
    @Size(max = 50)
    @NotEmpty
    @Email
    private String email;

    @Size(max = 32)
    @NotEmpty
    private String password;

    @NotNull
    private List<TaskDto> authorTasks;

    @NotNull
    private List<TaskDto> assignedTasks;

    @NotNull
    private List<CommentsDto> comments;
}
