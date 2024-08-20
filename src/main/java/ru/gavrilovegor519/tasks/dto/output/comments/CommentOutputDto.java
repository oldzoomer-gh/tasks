package ru.gavrilovegor519.tasks.dto.output.comments;

import lombok.Getter;
import lombok.Setter;
import ru.gavrilovegor519.tasks.dto.output.users.UserDto;

@Getter
@Setter
public class CommentOutputDto {
    private Long id;

    private String text;

    private UserDto author;
}
