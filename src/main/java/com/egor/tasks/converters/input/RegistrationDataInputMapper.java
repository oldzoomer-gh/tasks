package com.egor.tasks.converters.input;

import com.egor.tasks.converters.util.EncodedMapping;
import com.egor.tasks.converters.util.PasswordEncoderMapper;
import com.egor.tasks.dto.input.LoginAndRegistrationDto;
import com.egor.tasks.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface RegistrationDataInputMapper {
    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User toEntity(LoginAndRegistrationDto dto);
}
