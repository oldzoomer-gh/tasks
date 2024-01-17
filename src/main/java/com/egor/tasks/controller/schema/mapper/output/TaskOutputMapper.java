package com.egor.tasks.controller.schema.mapper.output;

import com.egor.tasks.controller.schema.dto.output.OutputTaskDto;
import com.egor.tasks.controller.schema.dto.output.OutputUserDto;
import com.egor.tasks.dao.entity.Task;
import com.egor.tasks.dao.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskOutputMapper {
    OutputTaskDto map(Task task);

    OutputUserDto map(User user);
}
