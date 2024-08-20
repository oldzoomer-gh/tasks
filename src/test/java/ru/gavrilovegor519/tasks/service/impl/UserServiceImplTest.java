package ru.gavrilovegor519.tasks.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.gavrilovegor519.tasks.entity.User;
import ru.gavrilovegor519.tasks.exception.DuplicateUserException;
import ru.gavrilovegor519.tasks.exception.IncorrectPasswordException;
import ru.gavrilovegor519.tasks.exception.UserNotFoundException;
import ru.gavrilovegor519.tasks.repo.UserRepository;
import ru.gavrilovegor519.tasks.security.JwtUtilities;

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
    void loginWithExistUser() throws UserNotFoundException, IncorrectPasswordException {

        var loginDTO = new User();
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

        var loginDTO = new User();
        loginDTO.setEmail("1@1.ru");
        loginDTO.setPassword("test");

        var user = new User();
        user.setEmail(loginDTO.getEmail());
        user.setPassword(passwordEncoder.encode("test2"));

        when(userRepository.findByEmail("1@1.ru")).thenReturn(Optional.of(user));

        assertThrows(IncorrectPasswordException.class, () -> userService.login(loginDTO));
    }

    @Test
    void loginWithNotExistUser() {

        var loginDTO = new User();
        loginDTO.setEmail("test1");

        assertThrows(UserNotFoundException.class, () -> userService.login(loginDTO));
    }

    @Test
    void registrationWithDuplicatedUser() {

        var userData = new User();
        userData.setEmail("1@1.ru");

        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(DuplicateUserException.class, () -> userService.reg(userData));
    }
}