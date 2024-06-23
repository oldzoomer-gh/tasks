package com.egor.tasks.dto.output.comments;

import com.egor.tasks.dto.UserDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentOutputDto {
    private Long id;

    @Size(max = 300, message = "Comment must be less than 300 characters")
    @NotEmpty(message = "Comment can't be empty")
    private String text;

    private UserDto author;
}
