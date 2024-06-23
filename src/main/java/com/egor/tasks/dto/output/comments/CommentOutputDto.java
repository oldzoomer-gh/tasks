package com.egor.tasks.dto.output.comments;

import com.egor.tasks.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentOutputDto {
    private Long id;

    private String text;

    private UserDto author;
}
