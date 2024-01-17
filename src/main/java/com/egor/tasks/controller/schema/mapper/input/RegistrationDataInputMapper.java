package com.egor.tasks.controller.schema.mapper.input;

import com.egor.tasks.controller.schema.mapper.util.EncodedMapping;
import com.egor.tasks.controller.schema.mapper.util.PasswordEncoderMapper;
import com.egor.tasks.controller.schema.dto.input.LoginAndRegistrationDto;
import com.egor.tasks.dao.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface RegistrationDataInputMapper {
    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User map(LoginAndRegistrationDto dto);
}
