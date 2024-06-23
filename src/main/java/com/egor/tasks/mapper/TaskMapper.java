package com.egor.tasks.mapper;

import com.egor.tasks.dto.input.tasks.CreateTaskDto;
import com.egor.tasks.dto.input.tasks.EditTaskDto;
import com.egor.tasks.dto.output.tasks.TaskOutputDto;
import com.egor.tasks.dto.output.users.UserDto;
import com.egor.tasks.entity.Task;
import com.egor.tasks.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    Task map(CreateTaskDto dto);

    Task map(EditTaskDto dto);

    TaskOutputDto map(Task task);

    UserDto map(User user);
}
