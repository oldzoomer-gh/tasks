package com.egor.tasks.converters;

import com.egor.tasks.dto.UserDto;
import com.egor.tasks.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationDataConverter implements Converter<UserDto, User> {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User convert(UserDto userDto) {
        var builder = User.builder()
                .email(userDto.getEmail());

        builder.password(passwordEncoder.encode(userDto.getPassword()));

        return builder.build();
    }
}
