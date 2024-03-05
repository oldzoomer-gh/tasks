package com.egor.tasks.service.impl;

import com.egor.tasks.dto.input.LoginAndRegistrationDto;
import com.egor.tasks.entity.User;
import com.egor.tasks.exception.DuplicateUser;
import com.egor.tasks.exception.IncorrectPassword;
import com.egor.tasks.exception.UserNotFound;
import com.egor.tasks.repo.UserRepository;
import com.egor.tasks.security.JwtUtilities;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private JwtUtilities jwtUtilities;

    @Mock
    private UserRepository userRepository;

    @Spy
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loginWithExistUser() throws UserNotFound, IncorrectPassword {

        var loginDTO = new LoginAndRegistrationDto();
        loginDTO.setEmail("1@1.ru");
        loginDTO.setPassword("test");

        var user = new User();
        user.setEmail(loginDTO.getEmail());
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));

        when(userRepository.findByEmail("1@1.ru")).thenReturn(Optional.of(user));

        userService.login(loginDTO);
    }

    @Test
    void loginWithExistUserButWithIncorrectPassword() {

        var loginDTO = new LoginAndRegistrationDto();
        loginDTO.setEmail("1@1.ru");
        loginDTO.setPassword("test");

        var user = new User();
        user.setEmail(loginDTO.getEmail());
        user.setPassword(passwordEncoder.encode("test2"));

        when(userRepository.findByEmail("1@1.ru")).thenReturn(Optional.of(user));

        assertThrows(IncorrectPassword.class, () -> userService.login(loginDTO));
    }

    @Test
    void loginWithNotExistUser() {

        var loginDTO = new LoginAndRegistrationDto();
        loginDTO.setEmail("test1");

        assertThrows(UserNotFound.class, () -> userService.login(loginDTO));
    }

    @Test
    void registrationWithDuplicatedUser() {

        var userData = new LoginAndRegistrationDto();
        userData.setEmail("1@1.ru");

        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(DuplicateUser.class, () -> userService.reg(userData));
    }
}