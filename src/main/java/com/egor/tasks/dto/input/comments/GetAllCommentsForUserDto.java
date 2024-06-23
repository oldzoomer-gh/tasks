package com.egor.tasks.dto.input.comments;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllCommentsForUserDto {
    private Integer start;

    private Integer end;

    private String email;
}
