package com.egor.tasks.controller.dto.converters.input;

import com.egor.tasks.controller.dto.converters.util.EncodedMapping;
import com.egor.tasks.controller.dto.converters.util.PasswordEncoderMapper;
import com.egor.tasks.controller.dto.input.LoginAndRegistrationDto;
import com.egor.tasks.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface RegistrationDataInputMapper {
    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User map(LoginAndRegistrationDto dto);
}
