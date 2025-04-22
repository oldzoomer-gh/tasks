package ru.gavrilovegor519.tasks.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.gavrilovegor519.tasks.dto.input.users.LoginDto;
import ru.gavrilovegor519.tasks.dto.input.users.RegDto;
import ru.gavrilovegor519.tasks.entity.User;
import ru.gavrilovegor519.tasks.mapper.util.EncodedMapping;
import ru.gavrilovegor519.tasks.mapper.util.PasswordEncoderMapper;

@Mapper(componentModel = "spring",
        uses = PasswordEncoderMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(source = "password", target = "password")
    User map(LoginDto dto);

    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User map(RegDto dto);
}
