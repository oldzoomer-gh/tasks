package com.egor.tasks.converters;

import com.egor.tasks.dto.TaskDto;
import com.egor.tasks.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);
    Task toEntity(TaskDto taskDto);
}
