package com.egor.tasks.service.impl;

import com.egor.tasks.dto.TokenDto;
import com.egor.tasks.dto.UserDto;
import com.egor.tasks.entity.User;
import com.egor.tasks.exception.DuplicateUserException;
import com.egor.tasks.exception.IncorrectPasswordException;
import com.egor.tasks.exception.UserNotFoundException;
import com.egor.tasks.mapper.UserMapper;
import com.egor.tasks.repo.UserRepository;
import com.egor.tasks.security.JwtUtilities;
import com.egor.tasks.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtUtilities jwtUtilities;
    private final UserRepository userRepository;
    private final UserMapper registrationDataInputMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public TokenDto login(UserDto loginData) {
        String email = loginData.getEmail();
        String password = loginData.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password!");
        }

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwtUtilities.generateToken(user.getUsername(), "ROLE_USER"));
        return tokenDto;
    }

    @Override
    public void reg(UserDto userData) {
        boolean emailIsExist =
                userRepository.existsByEmail(userData.getEmail());

        if (emailIsExist) {
            throw new DuplicateUserException("Duplicate E-Mail.");
        }

        User user = registrationDataInputMapper.map(userData);
        userRepository.save(user);
    }
}
