package com.egor.tasks.dto.input.comments;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllCommentsForTaskDto {
    private Integer start;

    private Integer end;

    private Long taskId;
}
