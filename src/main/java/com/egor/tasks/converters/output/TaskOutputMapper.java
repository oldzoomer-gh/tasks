package com.egor.tasks.converters.output;

import com.egor.tasks.dto.output.OutputTaskDto;
import com.egor.tasks.dto.output.OutputUserDto;
import com.egor.tasks.entity.Task;
import com.egor.tasks.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskOutputMapper {
    OutputTaskDto map(Task task);

    OutputUserDto map(User user);
}
