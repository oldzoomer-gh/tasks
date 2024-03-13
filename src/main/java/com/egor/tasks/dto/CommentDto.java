package com.egor.tasks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    @Size(max = 300, message = "Comment must be less than 300 characters")
    @NotEmpty(message = "Comment can't be empty")
    private String text;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto author;
}
