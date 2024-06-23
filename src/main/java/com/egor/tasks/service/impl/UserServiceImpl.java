package com.egor.tasks.service.impl;

import com.egor.tasks.dto.output.users.TokenDto;
import com.egor.tasks.entity.User;
import com.egor.tasks.exception.DuplicateUserException;
import com.egor.tasks.exception.IncorrectPasswordException;
import com.egor.tasks.exception.UserNotFoundException;
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
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public TokenDto login(User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        User user1 = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found."));

        if (!passwordEncoder.matches(password, user1.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password!");
        }

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwtUtilities.generateToken(user1.getUsername(), "ROLE_USER"));
        return tokenDto;
    }

    @Override
    public void reg(User user) {
        boolean emailIsExist =
                userRepository.existsByEmail(user.getEmail());

        if (emailIsExist) {
            throw new DuplicateUserException("Duplicate E-Mail.");
        }

        userRepository.save(user);
    }
}
