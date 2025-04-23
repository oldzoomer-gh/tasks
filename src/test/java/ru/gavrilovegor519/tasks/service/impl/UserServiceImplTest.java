package ru.gavrilovegor519.tasks.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private JwtUtilities jwtUtilities;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loginWithExistUser() throws UserNotFoundException, IncorrectPasswordException {
        User loginDto = mock(User.class);
        User user = mock(User.class);

        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        userService.login(loginDto);

        verify(jwtUtilities).generateToken(user.getUsername(), "ROLE_USER");
    }

    @Test
    void loginWithExistUserButWithIncorrectPassword() {
        User loginDto = mock(User.class);
        User user = mock(User.class);

        when(passwordEncoder.matches(any(), any())).thenReturn(false);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        assertThrows(IncorrectPasswordException.class, () -> userService.login(loginDto));
    }

    @Test
    void loginWithNotExistUser() {
        User loginDto = mock(User.class);

        when(loginDto.getEmail()).thenReturn("test@example.com");

        assertThrows(UserNotFoundException.class, () -> userService.login(loginDto));
    }

    @Test
    void registrationWithDuplicatedUser() {
        User loginDto = mock(User.class);

        when(loginDto.getEmail()).thenReturn("test@example.com");

        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(DuplicateUserException.class, () -> userService.reg(loginDto));
    }
}
