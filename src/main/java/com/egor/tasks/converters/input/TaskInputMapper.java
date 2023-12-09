package com.egor.tasks.converters.input;

import com.egor.tasks.dto.input.CreateTaskDto;
import com.egor.tasks.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskInputMapper {
    Task toEntity(CreateTaskDto createTaskDto);
}
