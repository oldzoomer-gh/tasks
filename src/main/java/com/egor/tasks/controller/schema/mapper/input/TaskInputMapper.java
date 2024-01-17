package com.egor.tasks.controller.schema.mapper.input;

import com.egor.tasks.controller.schema.dto.input.CreateTaskDto;
import com.egor.tasks.dao.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskInputMapper {
    Task map(CreateTaskDto createTaskDto);
}
