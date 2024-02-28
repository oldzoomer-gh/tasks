package com.egor.tasks.service.impl;

import com.egor.tasks.dto.input.LoginAndRegistrationDto;
import com.egor.tasks.dto.output.TokenDto;
import com.egor.tasks.entity.User;
import com.egor.tasks.exception.DuplicateUser;
import com.egor.tasks.exception.IncorrectPassword;
import com.egor.tasks.exception.UserNotFound;
import com.egor.tasks.mapper.input.RegistrationDataInputMapper;
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
    private final RegistrationDataInputMapper registrationDataInputMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public TokenDto login(LoginAndRegistrationDto loginData) throws IncorrectPassword, UserNotFound {
        String email = loginData.getEmail();
        String password = loginData.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFound("User not found."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPassword("Incorrect password!");
        }

        return TokenDto.builder()
                .token(jwtUtilities.generateToken(user.getUsername(), "ROLE_USER"))
                .build();
    }

    @Override
    public void reg(LoginAndRegistrationDto userData) throws DuplicateUser {
        boolean emailIsExist =
                userRepository.existsByEmail(userData.getEmail());

        if (emailIsExist) {
            throw new DuplicateUser("Duplicate E-Mail.");
        }

        User user = registrationDataInputMapper.map(userData);
        assert user != null;
        userRepository.save(user);
    }
}
