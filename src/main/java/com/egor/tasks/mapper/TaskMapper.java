package com.egor.tasks.mapper;

import com.egor.tasks.dto.TaskDto;
import com.egor.tasks.dto.UserDto;
import com.egor.tasks.entity.Task;
import com.egor.tasks.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    Task map(TaskDto dto);

    TaskDto map(Task task);

    UserDto map(User user);
}
