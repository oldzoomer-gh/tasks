package com.egor.tasks.mapper.input;

import com.egor.tasks.dto.input.CreateTaskDto;
import com.egor.tasks.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskInputMapper {
    Task map(CreateTaskDto createTaskDto);
}
