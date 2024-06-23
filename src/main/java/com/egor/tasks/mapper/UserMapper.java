package com.egor.tasks.mapper;

import com.egor.tasks.dto.UserDto;
import com.egor.tasks.entity.User;
import com.egor.tasks.mapper.util.EncodedMapping;
import com.egor.tasks.mapper.util.PasswordEncoderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = PasswordEncoderMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User map(UserDto dto);

    UserDto map(User user);
}
