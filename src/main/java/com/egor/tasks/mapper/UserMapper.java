package com.egor.tasks.mapper;

import com.egor.tasks.dto.input.users.LoginDto;
import com.egor.tasks.dto.input.users.RegDto;
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
    User map(LoginDto dto);

    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User map(RegDto dto);
}
