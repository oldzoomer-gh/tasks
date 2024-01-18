package com.egor.tasks.controller.dto.converters.input;

import com.egor.tasks.controller.dto.input.CreateTaskDto;
import com.egor.tasks.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskInputMapper {
    Task map(CreateTaskDto createTaskDto);
}
