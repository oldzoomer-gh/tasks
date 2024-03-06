package com.egor.tasks.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutputCommentsDto {
    private OutputUserDto author;

    private OutputTaskDto task;

    private String text;
}
