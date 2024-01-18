package com.egor.tasks.mapper.input;

import com.egor.tasks.dto.input.LoginAndRegistrationDto;
import com.egor.tasks.entity.User;
import com.egor.tasks.mapper.util.EncodedMapping;
import com.egor.tasks.mapper.util.PasswordEncoderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface RegistrationDataInputMapper {
    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User map(LoginAndRegistrationDto dto);
}
