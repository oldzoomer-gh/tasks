package com.egor.tasks.controller.schema.mapper.output;

import com.egor.tasks.controller.schema.dto.output.OutputCommentsDto;
import com.egor.tasks.controller.schema.dto.output.OutputTaskDto;
import com.egor.tasks.controller.schema.dto.output.OutputUserDto;
import com.egor.tasks.dao.entity.Comments;
import com.egor.tasks.dao.entity.Task;
import com.egor.tasks.dao.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentsOutputMapper {
    OutputCommentsDto map(Comments comments);

    OutputTaskDto map(Task task);

    OutputUserDto map(User user);
}
