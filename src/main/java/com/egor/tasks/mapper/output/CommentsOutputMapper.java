package com.egor.tasks.mapper.output;

import com.egor.tasks.dto.output.OutputCommentsDto;
import com.egor.tasks.dto.output.OutputTaskDto;
import com.egor.tasks.dto.output.OutputUserDto;
import com.egor.tasks.entity.Comments;
import com.egor.tasks.entity.Task;
import com.egor.tasks.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentsOutputMapper {
    OutputCommentsDto map(Comments comments);

    OutputTaskDto map(Task task);

    OutputUserDto map(User user);
}
