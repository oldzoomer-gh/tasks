package com.egor.tasks.converters;

import com.egor.tasks.dto.LoginAndRegistrationDto;
import com.egor.tasks.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationDataConverter implements Converter<LoginAndRegistrationDto, User> {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User convert(LoginAndRegistrationDto loginAndRegistrationDto) {
        var builder = User.builder()
                .email(loginAndRegistrationDto.getEmail());

        builder.password(passwordEncoder.encode(loginAndRegistrationDto.getPassword()));

        return builder.build();
    }
}
