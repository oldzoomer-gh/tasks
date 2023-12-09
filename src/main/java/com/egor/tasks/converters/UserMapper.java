package com.egor.tasks.converters;

import com.egor.tasks.dto.UserDto;
import com.egor.tasks.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
